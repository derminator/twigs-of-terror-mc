package com.github.derminator.derminatorsMcMod

import net.neoforged.bus.api.SubscribeEvent
import net.neoforged.neoforge.event.entity.player.PlayerEvent
import org.slf4j.LoggerFactory

object EventHandlers {
    private val LOGGER = LoggerFactory.getLogger(EventHandlers::class.java)

    @SubscribeEvent
    fun onPlayerJoin(event: PlayerEvent.PlayerLoggedInEvent) {
        val player = event.entity

        LOGGER.info("Player ${player.name.contents} joined the game")
    }
}