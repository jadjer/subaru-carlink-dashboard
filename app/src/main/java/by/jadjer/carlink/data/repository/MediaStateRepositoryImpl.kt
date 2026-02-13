package by.jadjer.carlink.data.repository

import by.jadjer.carlink.domain.model.MediaState
import by.jadjer.carlink.domain.repository.MediaStateRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class MediaStateRepositoryImpl : MediaStateRepository {
    private val _state = MutableStateFlow(MediaState())

    val state: StateFlow<MediaState> = _state.asStateFlow()

    override fun updateState(newState: MediaState) {
        _state.value = newState
    }
}