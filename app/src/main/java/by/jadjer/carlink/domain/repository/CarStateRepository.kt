package by.jadjer.carlink.domain.repository

import by.jadjer.carlink.domain.model.MediaState

interface CarStateRepository {
    fun updateState(newState: MediaState);
}