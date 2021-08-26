package io.github.monull.customentity.protocol

import io.github.monull.customentity.loader.LibraryLoader

fun Double.toProtocolDelta(): Int {
    return (this.coerceIn(-3.9, 3.9) * 8000.0).toInt()
}

fun Float.toProtocolDegrees(): Int {
    val i = (this * 256.0F / 360.0F).toInt()

    return if (i < i.toFloat()) i - 1 else i
}

interface PacketSupport {

    companion object : PacketSupport by LibraryLoader.loadNMS(PacketSupport::class.java)


    fun payLoad(
        channel: String,
        byteArray: ByteArray
    ): PacketContainer
}