package by.jadjer.carlink.di

import android.content.Context
import android.hardware.usb.UsbManager
import by.jadjer.carlink.data.repository.CarStateRepositoryImpl
import by.jadjer.carlink.data.repository.MediaStateRepositoryImpl
import by.jadjer.carlink.data.repository.UsbRepositoryImpl
import by.jadjer.carlink.domain.repository.CarStateRepository
import by.jadjer.carlink.domain.repository.MediaStateRepository
import by.jadjer.carlink.domain.repository.UsbRepository

class AppContainerImpl(private val applicationContext: Context) : AppContainer {

    private val usbManager: UsbManager by lazy {
        applicationContext.getSystemService(Context.USB_SERVICE) as UsbManager
    }

    override val usbRepository: UsbRepository by lazy {
        UsbRepositoryImpl(applicationContext, usbManager)
    }

    override val carStateRepository: CarStateRepository by lazy {
        CarStateRepositoryImpl()
    }

    override val mediaStateRepository: MediaStateRepository by lazy {
        MediaStateRepositoryImpl()
    }
}
