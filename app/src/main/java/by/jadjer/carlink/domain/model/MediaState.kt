package by.jadjer.carlink.domain.model

data class MediaState(
    val trackTitle: String = "Unknown",
    val currentTime: String = "00:00",
    val volume: Int = 0,
    val source: String = "AUX", // CD, FM, AUX
    val eqBass: Int = 0
)