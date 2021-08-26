package io.github.xenon2452.customentity.packet

import java.io.ByteArrayOutputStream
import java.io.DataOutput
import java.io.DataOutputStream
import java.io.IOException
import com.google.common.base.Charsets;
import io.github.xenon2452.customentity.protocol.PacketContainer
import io.github.xenon2452.customentity.protocol.PacketSupport


class PacketBuilder private constructor() : DataOutput {
    private val bOut: ByteArrayOutputStream
    private val dOut: DataOutputStream
    lateinit var customEntityPacket: CustomEntityPacket
    override fun write(b: Int) {
        bOut.write(b)
    }

    override fun write(b: ByteArray) {
        try {
            bOut.write(b)
        } catch (e: IOException) {
            throw AssertionError(e)
        }
    }

    override fun write(b: ByteArray, off: Int, len: Int) {
        bOut.write(b, off, len)
    }

    override fun writeBoolean(v: Boolean) {
        try {
            dOut.writeBoolean(v)
        } catch (e: IOException) {
            throw AssertionError(e)
        }
    }

    override fun writeByte(v: Int) {
        try {
            dOut.writeByte(v)
        } catch (e: IOException) {
            throw AssertionError(e)
        }
    }

    override fun writeShort(v: Int) {
        try {
            dOut.writeShort(v)
        } catch (e: IOException) {
            throw AssertionError(e)
        }
    }

    override fun writeChar(v: Int) {
        try {
            dOut.writeChar(v)
        } catch (e: IOException) {
            throw AssertionError(e)
        }
    }

    override fun writeInt(v: Int) {
        try {
            dOut.writeInt(v)
        } catch (e: IOException) {
            throw AssertionError(e)
        }
    }

    override fun writeLong(v: Long) {
        try {
            dOut.writeLong(v)
        } catch (e: IOException) {
            throw AssertionError(e)
        }
    }

    override fun writeFloat(v: Float) {
        try {
            dOut.writeFloat(v)
        } catch (e: IOException) {
            throw AssertionError(e)
        }
    }

    override fun writeDouble(v: Double) {
        try {
            dOut.writeDouble(v)
        } catch (e: IOException) {
            throw AssertionError(e)
        }
    }

    override fun writeBytes(s: String) {
        try {
            dOut.writeBytes(s)
        } catch (e: IOException) {
            throw AssertionError(e)
        }
    }

    override fun writeChars(s: String) {
        try {
            dOut.writeChars(s)
        } catch (e: IOException) {
            throw AssertionError(e)
        }
    }

    override fun writeUTF(s: String) {
        try {
            dOut.writeUTF(s)
        } catch (e: IOException) {
            throw AssertionError(e)
        }
    }

    fun reset() {
        bOut.reset()
    }

    fun toByteArray(): ByteArray {
        return bOut.toByteArray()
    }

    fun build(channel: String): PacketContainer {
        return PacketSupport.payLoad(channel, toByteArray())
    }

    companion object {
        private val INSTANCE = PacketBuilder()
        val instance: PacketBuilder
            get() {
                INSTANCE.reset()
                return INSTANCE
            }
    }

    init {
        val bOut = ByteArrayOutputStream()
        this.bOut = bOut
        dOut = DataOutputStream(bOut)
    }
}