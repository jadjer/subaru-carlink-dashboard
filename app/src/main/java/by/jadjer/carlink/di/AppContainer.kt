package by.jadjer.carlink.di

import by.jadjer.carlink.domain.repository.CarStateRepository
import by.jadjer.carlink.domain.repository.MediaStateRepository
import by.jadjer.carlink.domain.repository.UsbRepository

interface AppContainer {
    val usbRepository: UsbRepository
    val carStateRepository: CarStateRepository
    val mediaStateRepository: MediaStateRepository
}
