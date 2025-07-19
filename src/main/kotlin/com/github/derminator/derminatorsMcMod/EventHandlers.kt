package com.github.derminator.derminatorsMcMod

import net.neoforged.bus.api.SubscribeEvent
import net.neoforged.neoforge.event.tick.ServerTickEvent

object EventHandlers {
    val playersNeedingStick = mutableMapOf<String, Int>()

    @SubscribeEvent
    fun onServerTick(event: ServerTickEvent.Post) {
        val players = event.server.playerList.players
        if (!players.isEmpty()) {
            val player = players.random()
            val stickTime = playersNeedingStick[player.name.string] ?: 0
            if (stickTime <= event.server.tickCount) {
                giveStick(player)
                playersNeedingStick[player.name.string] = event.server.tickCount + 100
            }
        }
    }
}
