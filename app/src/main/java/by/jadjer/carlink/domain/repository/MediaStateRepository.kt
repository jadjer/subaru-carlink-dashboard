package by.jadjer.carlink.domain.repository

import by.jadjer.carlink.domain.model.MediaState

interface MediaStateRepository {
    fun updateState(newState: MediaState);
}
