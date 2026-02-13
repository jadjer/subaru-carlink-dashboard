package by.jadjer.carlink.di

import android.content.Context
import by.jadjer.carlink.data.repository.CarStateRepositoryImpl
import by.jadjer.carlink.data.repository.MediaStateRepositoryImpl
import by.jadjer.carlink.domain.repository.CarStateRepository
import by.jadjer.carlink.domain.repository.MediaStateRepository

class AppContainerImpl(private val applicationContext: Context) : AppContainer {

    override val carStateRepository: CarStateRepository by lazy {
        CarStateRepositoryImpl()
    }

    override val mediaStateRepository: MediaStateRepository by lazy {
        MediaStateRepositoryImpl()
    }
}
