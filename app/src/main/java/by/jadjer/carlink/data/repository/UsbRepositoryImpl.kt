package by.jadjer.carlink.data.repository

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.hardware.usb.UsbDevice
import android.hardware.usb.UsbManager
import android.os.Build
import by.jadjer.carlink.domain.repository.UsbRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class UsbRepositoryImpl(context: Context, private val usbManager: UsbManager) : UsbRepository {
    private val _selectedDevice = MutableStateFlow<UsbDevice?>(null)
    private val _availableDevices = MutableStateFlow<List<UsbDevice>>(emptyList())
    private val usbDetachReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            if (intent.action == UsbManager.ACTION_USB_DEVICE_DETACHED) {
                val device = getParcelableDevice(intent)
                // Если отключенное устройство — это то, с которым мы работали
                if (device != null && device.deviceId == _selectedDevice.value?.deviceId) {
                    clearSelectedDevice() // Обнуляем StateFlow
                }
            }

            scanDevices()
        }
    }

    override val selectedDevice: StateFlow<UsbDevice?> = _selectedDevice
    override val availableDevices: StateFlow<List<UsbDevice>> = _availableDevices.asStateFlow()

    init {
        val filter = IntentFilter(UsbManager.ACTION_USB_DEVICE_DETACHED)
        context.registerReceiver(usbDetachReceiver, filter)

        scanDevices()
    }

    override fun scanDevices() {
        _availableDevices.value = usbManager.deviceList.values.toList()
    }

    override fun hasPermission(device: UsbDevice): Boolean {
        return usbManager.hasPermission(device)
    }

    override fun getAlreadyAuthorizedDevice(): UsbDevice? {
        return usbManager.deviceList.values.find { usbManager.hasPermission(it) }
    }

    override fun selectDevice(device: UsbDevice) {
        _selectedDevice.value = device
    }

    override fun clearSelectedDevice() {
        _selectedDevice.value = null
    }

    /**
     * Безопасное извлечение UsbDevice из интента с учетом версии Android (API 33+)
     */
    private fun getParcelableDevice(intent: Intent): UsbDevice? {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent.getParcelableExtra(UsbManager.EXTRA_DEVICE, UsbDevice::class.java)
        } else {
            @Suppress("DEPRECATION")
            intent.getParcelableExtra(UsbManager.EXTRA_DEVICE)
        }
    }
}