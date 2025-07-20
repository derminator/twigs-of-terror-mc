package com.github.derminator.derminatorsMcMod

import net.neoforged.bus.api.SubscribeEvent
import net.neoforged.neoforge.event.tick.ServerTickEvent
import org.slf4j.LoggerFactory
import kotlin.math.max
import kotlin.math.min
import kotlin.random.Random

object EventHandlers {
    private val playersNeedingStick = mutableMapOf<String, Int>()
    private val LOGGER = LoggerFactory.getLogger(this::class.java)

    // Constants for tick calculations (20 ticks = 1 second)
    private const val TICKS_PER_MINUTE = 20 * 60
    private const val MIN_WAIT_TICKS = 5 * TICKS_PER_MINUTE     // 5 minutes
    private const val MAX_WAIT_TICKS = 5 * 60 * TICKS_PER_MINUTE // 5 hours
    private const val MAX_WAIT_DITHER = 60 * TICKS_PER_MINUTE
    private const val MIN_WAIT_DITHER = -MAX_WAIT_DITHER

    /**
     * Determines how many ticks between stick gifts a player should wait based on their username.
     * Results range from 5 minutes to 5 hours.
     *
     * @param username The player's username
     * @return Number of ticks to wait before giving another stick
     */
    private fun calculateStickWaitTime(username: String): Int {
        val normalizedValue = 1 - getUserEventProbability(username)

        // Calculate ticks between MIN_WAIT_TICKS and MAX_WAIT_TICKS
        val waitTicks = MIN_WAIT_TICKS + (normalizedValue * (MAX_WAIT_TICKS - MIN_WAIT_TICKS)).toInt()

        val adjustedWaitTicks = waitTicks + Random.nextInt(MIN_WAIT_DITHER, MAX_WAIT_DITHER)

        // Ensure the result is within bounds
        return max(MIN_WAIT_TICKS, min(adjustedWaitTicks, MAX_WAIT_TICKS)).also {
            LOGGER.info("$username will get another stick in ${it / (TICKS_PER_MINUTE / 60)} seconds")
        }
    }

    @SubscribeEvent
    fun onServerTick(event: ServerTickEvent.Post) {
        val players = event.server.playerList.players
        if (!players.isEmpty()) {
            val player = players.random()
            val stickTime = playersNeedingStick[player.name.string] ?: 0
            if (stickTime <= event.server.tickCount) {
                giveStick(player)
                playersNeedingStick[player.name.string] =
                    event.server.tickCount + calculateStickWaitTime(player.name.string)
            }
        }
    }
}
