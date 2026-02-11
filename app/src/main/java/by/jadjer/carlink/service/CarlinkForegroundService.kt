package by.jadjer.carlink.service

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Context
import android.content.Intent
import android.hardware.usb.UsbManager
import android.os.Binder
import android.os.IBinder
import androidx.core.app.NotificationCompat
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class CarlinkForegroundService : Service() {
    private val _binder = LocalBinder()
    private val _counter = MutableStateFlow(0)
    private var _job: Job? = null

    companion object {
        const val NOTIFICATION_ID = 101
        const val CHANNEL_ID = "CARLINK_SERVICE_CHANNEL"
        const val CHANNEL_NAME = "Carlink Service Channel"
        const val VENDOR_ID = "303a"
        const val PRODUCT_ID = "4001"
    }

    val manager = getSystemService(Context.USB_SERVICE) as UsbManager
    val deviceList = manager.deviceList

//    val targetDevice = deviceList.values.find { it.vendorId == VENDOR_ID && it.productId == PRODUCT_ID }
    val counterFlow: StateFlow<Int> = _counter.asStateFlow()

    override fun onCreate() {
        super.onCreate()

        _job = CoroutineScope(Dispatchers.IO).launch {
            while (true) {
                delay(1000)
                _counter.value += 1
            }
        }
    }

    override fun onDestroy() {
        _job?.cancel()

        super.onDestroy()
    }

    inner class LocalBinder : Binder() {
        fun getService(): CarlinkForegroundService = this@CarlinkForegroundService
    }

    override fun onBind(intent: Intent): IBinder = _binder

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        createNotificationChannel()

        val notification = NotificationCompat.Builder(this, CHANNEL_ID)
            .setContentTitle("Сервис запущен")
            .setContentText("Выполнение фоновых задач...")
            .setSmallIcon(android.R.drawable.ic_dialog_info)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .build()

        startForeground(NOTIFICATION_ID, notification)

        return START_STICKY
    }

    private fun createNotificationChannel() {
        val channel = NotificationChannel(CHANNEL_ID, CHANNEL_NAME, NotificationManager.IMPORTANCE_DEFAULT)
        val manager = getSystemService(NotificationManager::class.java)
        manager.createNotificationChannel(channel)
    }
}