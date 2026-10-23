package com.oussama.island
import android.app.Notification
import android.content.Intent
import android.graphics.Bitmap
import android.service.notification.NotificationListenerService
import android.service.notification.StatusBarNotification
class NotifListener : NotificationListenerService() {
    override fun onNotificationPosted(sbn: StatusBarNotification) {
        val extras = sbn.notification.extras
        val title = extras.getString(Notification.EXTRA_TITLE) ?: ""
        val text = extras.getString(Notification.EXTRA_TEXT) ?: ""
        val largeIcon = extras.getParcelable<Bitmap>(Notification.EXTRA_LARGE_ICON)
        val intent = Intent(this, IslandService::class.java).apply {
            action = "UPDATE_ISLAND"
            putExtra("song_title", title)
            putExtra("song_artist", text)
            putExtra("album_art", largeIcon)
        }
        startService(intent)
    }
}
