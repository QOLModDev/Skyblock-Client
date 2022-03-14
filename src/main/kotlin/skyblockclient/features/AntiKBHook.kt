package skyblockclient.features

import net.minecraft.network.play.server.S12PacketEntityVelocity
import net.minecraft.network.play.server.S27PacketExplosion
import skyblockclient.SkyblockClient.Companion.config
import skyblockclient.SkyblockClient.Companion.inSkyblock
import skyblockclient.SkyblockClient.Companion.mc
import skyblockclient.utils.Utils.equalsOneOf
import skyblockclient.utils.Utils.itemID

object AntiKBHook {
    fun handleExplosion(packet: S27PacketExplosion) {
        if (config.antiKBSkyblock && inSkyblock && !disableAntiKB()) {
            mc.thePlayer.motionX -= packet.func_149149_c().toDouble()
            mc.thePlayer.motionY -= packet.func_149144_d().toDouble()
            mc.thePlayer.motionZ -= packet.func_149147_e().toDouble()
        }
    }

    fun handleEntityVelocity(packet: S12PacketEntityVelocity) {
        if (config.antiKBSkyblock && inSkyblock && !disableAntiKB()) {
            if (mc.theWorld.getEntityByID(packet.entityID) == mc.thePlayer) {
                mc.thePlayer.setVelocity(0.0, 0.0, 0.0)
            }
        }
    }

    private fun disableAntiKB(): Boolean = mc.thePlayer.isInLava || mc.thePlayer.heldItem?.itemID.equalsOneOf(
        "JERRY_STAFF",
        "BONZO_STAFF",
        "STARRED_BONZO_STAFF"
    )
}