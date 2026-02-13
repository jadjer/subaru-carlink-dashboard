package by.jadjer.carlink.service

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.IBinder
import androidx.core.app.NotificationCompat

class CarlinkForegroundService : Service() {
    companion object {
        const val NOTIFICATION_ID = 101
        const val CHANNEL_ID = "CARLINK_SERVICE_CHANNEL"
        const val CHANNEL_NAME = "Carlink Service Channel"
        const val VENDOR_ID = "303a"
        const val PRODUCT_ID = "4001"
    }

    private val _binder = LocalBinder()

    inner class LocalBinder : Binder() {
        fun getService(): CarlinkForegroundService = this@CarlinkForegroundService
    }

    override fun onCreate() {
        super.onCreate()

        createNotificationChannel()

        startForeground(1, createNotification())
    }

    override fun onBind(intent: Intent): IBinder {
        return _binder
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        super.onStartCommand(intent, flags, startId)
        return START_STICKY
    }

    override fun onDestroy() {
        stopForeground(STOP_FOREGROUND_REMOVE)
        super.onDestroy()
    }

    private fun createNotificationChannel() {
        val descriptionText = "Отображает уровень громкости внешнего устройства"
        val importance = NotificationManager.IMPORTANCE_HIGH
        val channel = NotificationChannel(CHANNEL_ID, CHANNEL_NAME, importance).apply {
            description = descriptionText
            setSound(null, null)
            enableVibration(false)
        }
        val notificationManager = getSystemService(NotificationManager::class.java)
        notificationManager.createNotificationChannel(channel)
    }

    private fun createNotification(): Notification {
        return NotificationCompat.Builder(this, CHANNEL_ID)
            .setContentTitle("Carlink Service")
            .setContentText("Мониторинг громкости USB активен")
            .setSmallIcon(android.R.drawable.ic_menu_compass)
            .setPriority(NotificationCompat.PRIORITY_LOW)
            .build()
    }

    private fun updateVolumeNotification(currentLevel: Int) {
        val notification = NotificationCompat.Builder(this, CHANNEL_ID)
            .setSmallIcon(android.R.drawable.ic_lock_silent_mode_off) // Ваша иконка
            .setContentTitle("Громкость USB: $currentLevel%")
            .setPriority(NotificationCompat.PRIORITY_HIGH) // LOW, чтобы не всплывало на каждый %
            .setCategory(NotificationCompat.CATEGORY_STATUS)
            .setOngoing(true) // Нельзя смахнуть
            .setOnlyAlertOnce(false) // КРИТИЧНО: предотвращает звуки и вибрацию при обновлении
            .setProgress(100, currentLevel, false) // 100 - макс, false - не бесконечный
            .setSilent(false) // Полная тишина
            .build()

        val notificationManager = getSystemService(NotificationManager::class.java)
        notificationManager.notify(NOTIFICATION_ID, notification)
    }
}