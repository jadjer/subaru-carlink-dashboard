package by.jadjer.carlink.domain.model

data class IEBusMessage(
    val master: UInt,
    val slave: UInt,
    val broadcast: BroadcastType,
    val control: UInt,
    val length: UInt,
    val payload: ByteArray,
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as IEBusMessage

        if (master != other.master) return false
        if (slave != other.slave) return false
        if (broadcast != other.broadcast) return false
        if (control != other.control) return false
        if (length != other.length) return false
        if (!payload.contentEquals(other.payload)) return false

        return true
    }

    override fun hashCode(): Int {
        var result = master.hashCode()
        result = 31 * result + slave.hashCode()
        result = 31 * result + broadcast.hashCode()
        result = 31 * result + control.hashCode()
        result = 31 * result + length.hashCode()
        result = 31 * result + payload.contentHashCode()
        return result
    }
}
