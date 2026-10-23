package com.oussama.island
import android.app.Service
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.PixelFormat
import android.os.IBinder
import android.view.Gravity
import android.view.WindowManager
import android.widget.LinearLayout
import android.widget.TextView
import androidx.palette.graphics.Palette
class IslandService : Service() {
    private lateinit var windowManager: WindowManager
    private lateinit var islandView: LinearLayout
    private lateinit var titleView: TextView
    private lateinit var params: WindowManager.LayoutParams
    private var isExpanded = false
    override fun onCreate() {
        super.onCreate()
        windowManager = getSystemService(WINDOW_SERVICE) as WindowManager
        islandView = LinearLayout(this).apply {
            orientation = LinearLayout.HORIZONTAL
            setPadding(40, 20, 40, 20)
            background = android.graphics.drawable.GradientDrawable().apply {
                cornerRadius = 60f
                setColor(Color.BLACK)
            }
        }
        titleView = TextView(this).apply {
            text = "●"
            setTextColor(Color.WHITE)
            textSize = 16f
        }
        islandView.addView(titleView)
        params = WindowManager.LayoutParams(
            WindowManager.LayoutParams.WRAP_CONTENT,
            WindowManager.LayoutParams.WRAP_CONTENT,
            WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY,
            WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
            PixelFormat.TRANSLUCENT
        ).apply {
            gravity = Gravity.TOP or Gravity.CENTER_HORIZONTAL
            y = 50
        }
        islandView.setOnClickListener {
            isExpanded = !isExpanded
            if (isExpanded) {
                titleView.text = "🎵 جاري التشغيل..."
                params.width = 600
                params.height = 150
            } else {
                titleView.text = "●"
                params.width = WindowManager.LayoutParams.WRAP_CONTENT
                params.height = WindowManager.LayoutParams.WRAP_CONTENT
            }
            windowManager.updateViewLayout(islandView, params)
        }
        windowManager.addView(islandView, params)
    }
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        if (intent?.action == "UPDATE_ISLAND") {
            val title = intent.getStringExtra("song_title")
            val artist = intent.getStringExtra("song_artist")
            val albumArt = intent.getParcelableExtra<Bitmap>("album_art")
            titleView.text = "$title - $artist"
            if (albumArt != null) {
                Palette.from(albumArt).generate { palette ->
                    val vibrantColor = palette?.vibrantSwatch?.rgb ?: palette?.dominantSwatch?.rgb
                    if (vibrantColor != null) {
                        (islandView.background as? android.graphics.drawable.GradientDrawable)?.setColor(vibrantColor)
                    }
                }
            }
        }
        return START_STICKY
    }
    override fun onDestroy() {
        super.onDestroy()
        if (::islandView.isInitialized) windowManager.removeView(islandView)
    }
    override fun onBind(intent: Intent?): IBinder? = null
}
