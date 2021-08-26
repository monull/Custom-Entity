package io.github.monull.customentity.plugin

import io.github.monun.kommand.kommand
import io.github.monull.customentity.packet.CustomEntityPacket
import io.github.monull.customentity.protocol.sendPacket
import net.kyori.adventure.text.Component.text
import org.bukkit.entity.Entity
import org.bukkit.entity.LivingEntity
import org.bukkit.entity.Player
import org.bukkit.plugin.java.JavaPlugin
import java.util.function.Predicate


class CustomEntityPlugin : JavaPlugin() {
    lateinit var packet: CustomEntityPacket
    override fun onEnable() {
        packet = CustomEntityPacket(this)
        kommand {
            register("customentity", "ce") {
                then("register") {
                    then("target") {
                        requires { playerOrNull != null }
                        executes {
                            val entity = selectTarget(sender as Player)
                            val packet = packet.register(entity.entityId)
                            server.sendPacket(packet)
                            feedback(text("CustomEntity로 등록합니다. ${entity.entityId}"))
                        }
                    }
                    then("id" to int()) {
                        executes {
                            val id: Int = it["id"]
                            val packet = packet.register(id)
                            server.sendPacket(packet)
                            feedback(text("CustomEntity로 등록합니다. $id"))
                        }
                    }
                }
                then("unregister") {
                    then("target") {
                        requires { playerOrNull != null }
                        executes {
                            val entity = selectTarget(sender as Player)
                            val packet = packet.unregister(entity.entityId)
                            server.sendPacket(packet)
                            feedback(text("CustomEntity를 해제합니다."))
                        }
                    }
                    then("id" to int()) {
                        executes {
                            val id: Int = it["id"]
                            val packet = packet.unregister(id)
                            server.sendPacket(packet)
                            feedback(text("CustomEntity를 해제합니다."))
                        }
                    }
                }
                then("scale") {
                    then("target") {
                        requires { playerOrNull != null }
                        then("x" to float(), "y" to float(), "z" to float()) {
                            then("duration" to int()) {
                                executes {
                                    val id = selectTarget(sender as Player).entityId
                                    val x: Float = it["x"]
                                    val y: Float = it["y"]
                                    val z: Float = it["z"]
                                    val duration: Int = it["duration"]
                                    val packet = packet.scale(id, x, y, z, duration)
                                    server.sendPacket(packet)
                                    feedback(text("크기를 적용합니다. $id -> $x $y $z $duration"))
                                }
                            }
                            executes {
                                val id = selectTarget(sender as Player).entityId
                                val x: Float = it["x"]
                                val y: Float = it["y"]
                                val z: Float = it["z"]
                                val packet = packet.scale(id, x, y, z, 0)
                                server.sendPacket(packet)
                                feedback(text("크기를 적용합니다. $id -> $x $y $z 0"))
                            }
                        }
                    }
                    then("id" to int()) {
                        then("x" to float(), "y" to float(), "z" to float()) {
                            then("duration" to int()) {
                                executes {
                                    val id: Int = it["id"]
                                    val x: Float = it["x"]
                                    val y: Float = it["y"]
                                    val z: Float = it["z"]
                                    val duration: Int = it["duration"]
                                    val packet = packet.scale(id, x, y, z, duration)
                                    server.sendPacket(packet)
                                    feedback(text("크기를 적용합니다. $id -> $x $y $z $duration"))
                                }
                            }
                            executes {
                                val id: Int = it["id"]
                                val x: Float = it["x"]
                                val y: Float = it["y"]
                                val z: Float = it["z"]
                                val packet = packet.scale(id, x, y, z, 0)
                                server.sendPacket(packet)
                                feedback(text("크기를 적용합니다. $id -> $x $y $z 0"))
                            }
                        }
                    }
                }
                then("color") {
                    then("target") {
                        requires { playerOrNull != null }
                        then("color" to int()) {
                            then("duration" to int()) {
                                executes {
                                    val id = selectTarget(sender as Player).entityId
                                    val color: Int = it["color"]
                                    val c = Integer.decode("0x$color")
                                    val r = c shr 16 and 0xFF
                                    val g = c shr 8 and 0xFF
                                    val b = c and 0xFF
                                    val duration: Int = it["duration"]
                                    val packet = packet.color(id, r, g, b, duration)
                                    server.sendPacket(packet)
                                    feedback(text("색상을 적용합니다. $id -> $color $duration"))
                                }
                            }
                            executes {
                                val id = selectTarget(sender as Player).entityId
                                val color: Int = it["color"]
                                val c = Integer.decode("0x$color")
                                val r = c shr 16 and 0xFF
                                val g = c shr 8 and 0xFF
                                val b = c and 0xFF
                                val packet = packet.color(id, r, g, b, 0)
                                server.sendPacket(packet)
                                feedback(text("색상을 적용합니다. $id -> $color 0"))
                            }
                        }
                    }
                    then("id" to int()) {
                        then("color" to int()) {
                            then("duration" to int()) {
                                executes {
                                    val id: Int = it["id"]
                                    val color: Int = it["color"]
                                    val c = Integer.decode("0x$color")
                                    val r = c shr 16 and 0xFF
                                    val g = c shr 8 and 0xFF
                                    val b = c and 0xFF
                                    val duration: Int = it["duration"]
                                    val packet = packet.color(id, r, g, b, duration)
                                    server.sendPacket(packet)
                                    feedback(text("색상을 적용합니다. $id -> $color $duration"))
                                }
                            }
                            executes {
                                val id: Int = it["id"]
                                val color: Int = it["color"]
                                val c = Integer.decode("0x$color")
                                val r = c shr 16 and 0xFF
                                val g = c shr 8 and 0xFF
                                val b = c and 0xFF
                                val packet = packet.color(id, r, g, b, 0)
                                server.sendPacket(packet)
                                feedback(text("색상을 적용합니다. $id -> $color 0"))
                            }
                        }
                    }
                }
                then("colorandscale") {
                    then("target") {
                        requires { playerOrNull != null }
                        then("color" to int(), "x" to float(), "y" to float(), "z" to float()) {
                            then("duration" to int()) {
                                executes {
                                    val id = selectTarget(sender as Player).entityId
                                    val color: Int = it["color"]
                                    val c = Integer.decode("0x$color")
                                    val r = c shr 16 and 0xFF
                                    val g = c shr 8 and 0xFF
                                    val b = c and 0xFF
                                    val x: Float = it["x"]
                                    val y: Float = it["y"]
                                    val z: Float = it["z"]
                                    val duration: Int = it["duration"]
                                    val packet = packet.colorAndScale(id, r, g, b, x, y, z, duration)
                                    server.sendPacket(packet)
                                    feedback(text("크기와 색상을 적용합니다. $id -> $color $x $y $z $duration"))
                                }
                            }
                            executes {
                                val id = selectTarget(sender as Player).entityId
                                val color: Int = it["color"]
                                val c = Integer.decode("0x$color")
                                val r = c shr 16 and 0xFF
                                val g = c shr 8 and 0xFF
                                val b = c and 0xFF
                                val x: Float = it["x"]
                                val y: Float = it["y"]
                                val z: Float = it["z"]
                                val packet = packet.colorAndScale(id, r, g, b, x, y, z, 0)
                                server.sendPacket(packet)
                                feedback(text("크기와 색상을 적용합니다. $id -> $color $x $y $z 0"))
                            }
                        }
                    }
                    then("id" to int()) {
                        then("color" to int(), "x" to float(), "y" to float(), "z" to float()) {
                            then("duration" to int()) {
                                executes {
                                    val id: Int = it["id"]
                                    val color: Int = it["color"]
                                    val c = Integer.decode("0x$color")
                                    val r = c shr 16 and 0xFF
                                    val g = c shr 8 and 0xFF
                                    val b = c and 0xFF
                                    val x: Float = it["x"]
                                    val y: Float = it["y"]
                                    val z: Float = it["z"]
                                    val duration: Int = it["duration"]
                                    val packet = packet.colorAndScale(id, r, g, b, x, y, z, duration)
                                    server.sendPacket(packet)
                                    feedback(text("크기와 색상을 적용합니다. $id -> $color $x $y $z $duration"))
                                }
                            }
                            executes {
                                val id: Int = it["id"]
                                val color: Int = it["color"]
                                val c = Integer.decode("0x$color")
                                val r = c shr 16 and 0xFF
                                val g = c shr 8 and 0xFF
                                val b = c and 0xFF
                                val x: Float = it["x"]
                                val y: Float = it["y"]
                                val z: Float = it["z"]
                                val packet = packet.colorAndScale(id, r, g, b, x, y, z, 0)
                                server.sendPacket(packet)
                                feedback(text("크기와 색상을 적용합니다. $id -> $color $x $y $z 0"))
                            }
                        }
                    }
                }
            }
        }
    }

    private fun selectTarget(player: Player): Entity {
        val loc = player.eyeLocation
        val direction = player.eyeLocation.direction
        val world = player.world
        val filter = Predicate<Entity> { entity ->
            when(entity) {
                player -> false
                is LivingEntity -> true
                else -> false
            }
        }
        val result = world.rayTraceEntities(loc, direction, 256.0, 1.0, filter)

        return result?.hitEntity!!
    }
}