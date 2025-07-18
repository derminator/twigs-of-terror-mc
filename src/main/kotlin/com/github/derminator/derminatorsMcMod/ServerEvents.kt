package com.github.derminator.derminatorsMcMod

import net.neoforged.api.distmarker.Dist
import net.neoforged.api.distmarker.OnlyIn
import net.neoforged.bus.api.SubscribeEvent
import net.neoforged.neoforge.event.entity.player.PlayerEvent

object ServerEvents {
    @SubscribeEvent
    @OnlyIn(Dist.DEDICATED_SERVER)
    fun onPlayerJoin(event: PlayerEvent.PlayerLoggedInEvent) {
        val player = event.entity

        println("Player ${player.name.contents} joined the game")
    }
}