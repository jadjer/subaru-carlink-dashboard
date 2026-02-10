package by.jadjer.carlink.ui.viewmodel

import android.content.ComponentName
import android.content.ServiceConnection
import android.os.IBinder
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import by.jadjer.carlink.service.CarlinkForegroundService
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ServiceViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(0)
    private var _collectJob: Job? = null

    val uiState: StateFlow<Int> = _uiState.asStateFlow()

    val connection = object : ServiceConnection {
        override fun onServiceConnected(name: ComponentName?, binder: IBinder?) {
            val service = (binder as CarlinkForegroundService.LocalBinder).getService()

            _collectJob = viewModelScope.launch {
                // Подписываемся на StateFlow сервиса
                service.counterFlow.collect { value ->
                    _uiState.value = value
                }
            }
        }

        override fun onServiceDisconnected(name: ComponentName?) {
            _collectJob?.cancel()
        }
    }
}
