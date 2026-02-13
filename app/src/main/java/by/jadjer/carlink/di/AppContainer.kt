package by.jadjer.carlink.di

import by.jadjer.carlink.domain.repository.CarStateRepository
import by.jadjer.carlink.domain.repository.MediaStateRepository

interface AppContainer {
    val carStateRepository: CarStateRepository
    val mediaStateRepository: MediaStateRepository
}
