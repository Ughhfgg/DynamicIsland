package com.oussama.island
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.os.LocaleListCompat
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val layout = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            setPadding(60, 60, 60, 60)
        }
        val welcomeText = TextView(this).apply {
            text = "مرحباً بك في Dynamic Island Pro\n\nيرجى تفعيل الصلاحيات بالترتيب:"
            textSize = 18f
            setPadding(0, 0, 0, 50)
        }
        val btnOverlay = Button(this).apply {
            text = "1. السماح بالرسم فوق التطبيقات"
            setOnClickListener {
                startActivity(Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION, Uri.parse("package:$packageName")))
            }
        }
        val btnNotif = Button(this).apply {
            text = "2. السماح بقراءة الإشعارات والأغاني"
            setOnClickListener {
                startActivity(Intent("android.settings.ACTION_NOTIFICATION_LISTENER_SETTINGS"))
            }
        }
        val btnStart = Button(this).apply {
            text = "3. تشغيل الجزيرة التفاعلية"
            setOnClickListener {
                startService(Intent(this@MainActivity, IslandService::class.java))
                finish()
            }
        }
        val btnLang = Button(this).apply {
            text = "تغيير اللغة (عربي / English)"
            setOnClickListener {
                val currentLang = AppCompatDelegate.getApplicationLocales()[0]?.language
                if (currentLang == "ar") {
                    AppCompatDelegate.setApplicationLocales(LocaleListCompat.forLanguageTags("en"))
                } else {
                    AppCompatDelegate.setApplicationLocales(LocaleListCompat.forLanguageTags("ar"))
                }
                recreate()
            }
        }
        layout.addView(welcomeText)
        layout.addView(btnOverlay)
        layout.addView(btnNotif)
        layout.addView(btnStart)
        layout.addView(btnLang)
        setContentView(layout)
    }
}
