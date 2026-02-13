package by.jadjer.carlink

import android.app.Application
import by.jadjer.carlink.di.AppContainer
import by.jadjer.carlink.di.AppContainerImpl


class CarlinkApplication : Application() {
    companion object {
//        const val JETNEWS_APP_URI = "https://developer.android.com/jetnews"
    }

    lateinit var container: AppContainer

    override fun onCreate() {
        super.onCreate()
        container = AppContainerImpl(this)
    }
}