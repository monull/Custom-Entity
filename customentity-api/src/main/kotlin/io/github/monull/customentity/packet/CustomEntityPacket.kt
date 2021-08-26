package io.github.monull.customentity.packet

import io.github.monull.customentity.protocol.PacketContainer
import org.bukkit.plugin.java.JavaPlugin

class CustomEntityPacket(plugin: JavaPlugin) {
    private val channel = "customentity:1.0"

    private val register = 0

    private val unregister = 1

    private val color = 2

    private val scale = 3

    private val colorAndScale = 4

    init {
        plugin.server.messenger.registerOutgoingPluginChannel(plugin, channel)
    }

    fun register(entityId: Int): PacketContainer {
        val builder = PacketBuilder.instance

        builder.write(register)
        builder.writeInt(entityId)

        return builder.build(channel)
    }

    fun unregister(vararg entityIds: Int): PacketContainer {
        val builder = PacketBuilder.instance

        builder.write(unregister)
        entityIds.forEach {
            builder.writeInt(it)
        }

        return builder.build(channel)
    }

    fun scale(entityId: Int, scaleX: Float, scaleY: Float, scaleZ: Float, duration: Int): PacketContainer {
        val builder = PacketBuilder.instance

        builder.write(scale)
        builder.writeInt(entityId)
        builder.writeFloat(scaleX)
        builder.writeFloat(scaleY)
        builder.writeFloat(scaleZ)
        builder.writeInt(duration)

        return builder.build(channel)
    }

    fun color(entityId: Int, colorR: Int, colorG: Int, colorB: Int, duration: Int): PacketContainer {
        val builder = PacketBuilder.instance

        builder.write(color)
        builder.writeInt(entityId)
        builder.writeInt(colorR)
        builder.writeInt(colorG)
        builder.writeInt(colorB)
        builder.writeInt(duration)

        return builder.build(channel)
    }

    fun colorAndScale(entityId: Int, colorR: Int, colorG: Int, colorB: Int, scaleX: Float, scaleY: Float, scaleZ: Float, duration: Int): PacketContainer {
        val builder = PacketBuilder.instance

        builder.write(colorAndScale)
        builder.writeInt(entityId)
        builder.writeInt(colorR)
        builder.writeInt(colorG)
        builder.writeInt(colorB)
        builder.writeFloat(scaleX)
        builder.writeFloat(scaleY)
        builder.writeFloat(scaleZ)
        builder.writeInt(duration)

        return builder.build(channel)
    }
}