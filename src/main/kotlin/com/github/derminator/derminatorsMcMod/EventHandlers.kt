package com.github.derminator.derminatorsMcMod

import net.neoforged.bus.api.SubscribeEvent
import net.neoforged.neoforge.event.tick.ServerTickEvent

object EventHandlers {
    @SubscribeEvent
    fun onServerTick(event: ServerTickEvent.Post) {
        val players = event.server.playerList.players
        if (!players.isEmpty()) {
            val player = players.random()
            giveStickIfNeeded(player)
        }
    }
}
