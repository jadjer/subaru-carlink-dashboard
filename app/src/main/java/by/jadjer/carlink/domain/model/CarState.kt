package by.jadjer.carlink.domain.model

data class CarState(
    val throttle: Int = 0,
    val fuelLevel: Int = 0,
    val RPM: Int = 0,
    val IAT: Int = 0,
    val ECT: Int = 0,
    val engineAirTemp: Float = 0f,
    val engineOTemp: Float = 0f,
    val gearboxTemp: Float = 0f,
)
