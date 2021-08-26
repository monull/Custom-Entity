package io.github.monull.customentity.v1_17_1.protocol

import io.github.monull.customentity.protocol.PacketContainer
import io.github.monull.customentity.protocol.PacketSupport
import io.netty.buffer.Unpooled
import net.minecraft.network.FriendlyByteBuf
import net.minecraft.network.protocol.game.ClientboundCustomPayloadPacket
import net.minecraft.resources.ResourceLocation

class NMSPacketSupport : PacketSupport {
    override fun payLoad(channel: String, byteArray: ByteArray): PacketContainer {

        val byteBuf = FriendlyByteBuf(Unpooled.buffer())
        byteBuf.writeResourceLocation(ResourceLocation(channel))
        byteBuf.writeByteArray(byteArray)

        val packet = ClientboundCustomPayloadPacket(byteBuf)
        return NMSPacketContainer(packet)
    }
}