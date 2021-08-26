package io.github.monull.customentity.v1_17_1.protocol

import io.github.monull.customentity.protocol.PacketContainer
import net.minecraft.network.protocol.Packet
import org.bukkit.craftbukkit.v1_17_R1.entity.CraftPlayer
import org.bukkit.entity.Player

class NMSPacketContainer(private val packet: Packet<*>): PacketContainer {
    override fun sendTo(player: Player) {
        (player as CraftPlayer).handle.connection.send(packet, null)
    }
}