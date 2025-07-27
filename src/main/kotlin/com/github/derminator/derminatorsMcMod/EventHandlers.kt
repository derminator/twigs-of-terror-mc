package com.github.derminator.derminatorsMcMod

import net.minecraft.world.entity.player.Player
import net.minecraft.world.item.Items
import net.neoforged.bus.api.SubscribeEvent
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent
import net.neoforged.neoforge.event.tick.ServerTickEvent

private const val MIN_DAMAGE_PROB = 0.001
private const val MAX_DAMAGE_PROB = 0.01
private const val ADJUSTED_DAMAGE = 9999.9f

object EventHandlers {
    @SubscribeEvent
    fun onServerTick(event: ServerTickEvent.Post) {
        val players = event.server.playerList.players
        if (!players.isEmpty()) {
            val player = players.random()
            giveStickIfNeeded(player)
        }
    }

    @SubscribeEvent
    fun onLivingDamage(event: LivingDamageEvent.Pre) {
        val source = event.source.entity
        if (event.source.weaponItem == Items.STICK && source is Player) {
            val prob =
                MIN_DAMAGE_PROB + (MAX_DAMAGE_PROB - MIN_DAMAGE_PROB) * getUserEventProbability(source.name.string)
            if (Math.random() < prob) {
                event.newDamage = ADJUSTED_DAMAGE
            }
        }
    }
}
