package by.jadjer.carlink.domain.repository

import android.hardware.usb.UsbDevice
import kotlinx.coroutines.flow.StateFlow

interface UsbRepository {
    /**
     * Список всех USB-устройств, подключенных к системе в данный момент.
     */
    val availableDevices: StateFlow<List<UsbDevice>>

    /**
     * Выбранное и подтвержденное пользователем устройство.
     * Если null — устройство не выбрано или не подключено.
     */
    val selectedDevice: StateFlow<UsbDevice?>

    /**
     * Обновить список доступных устройств (сканирование).
     */
    fun scanDevices()

    /**
     * Проверить, есть ли уже разрешение от системы на работу с конкретным устройством.
     */
    fun hasPermission(device: UsbDevice): Boolean

    /**
     * Найти устройство, на которое уже есть разрешение (сохраненное).
     * Полезно для автоматического старта при запуске приложения.
     */
    fun getAlreadyAuthorizedDevice(): UsbDevice?

    /**
     * Установить устройство как активное (после получения разрешения).
     */
    fun selectDevice(device: UsbDevice)

    /**
     * Сбросить текущее соединение (например, при отключении кабеля).
     */
    fun clearSelectedDevice()
}