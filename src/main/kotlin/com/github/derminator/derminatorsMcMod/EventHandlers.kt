package com.github.derminator.derminatorsMcMod

import net.neoforged.bus.api.SubscribeEvent
import net.neoforged.neoforge.event.tick.ServerTickEvent
import kotlin.math.max
import kotlin.math.min

object EventHandlers {
    val playersNeedingStick = mutableMapOf<String, Int>()

    // Constants for tick calculations (20 ticks = 1 second)
    private const val TICKS_PER_MINUTE = 20 * 60
    private const val MIN_WAIT_TICKS = 5 * TICKS_PER_MINUTE     // 5 minutes
    private const val MAX_WAIT_TICKS = 5 * 60 * TICKS_PER_MINUTE // 5 hours

    /**
     * Determines how many ticks between stick gifts a player should wait based on their username.
     * Results range from 5 minutes to 5 hours, with "CipheredEntity" naturally producing a result close to 5 minutes.
     * Uses the FNV-1a hash algorithm for consistent, deterministic results.
     *
     * @param username The player's username
     * @return Number of ticks to wait before giving another stick
     */
    fun calculateStickWaitTime(username: String): Int {
        val normalizedValue = hashUsername(username)


        // Calculate ticks between MIN_WAIT_TICKS and MAX_WAIT_TICKS
        val waitTicks = MIN_WAIT_TICKS + (normalizedValue * (MAX_WAIT_TICKS - MIN_WAIT_TICKS)).toInt()

        // Ensure the result is within bounds
        return max(MIN_WAIT_TICKS, min(waitTicks, MAX_WAIT_TICKS))
    }

    private fun hashUsername(username: String): Double {
        // Use FNV-1a hash algorithm for better distribution and determinism
        // FNV-1a constants for 32-bit hash
        val FNV_PRIME = 16777619
        val FNV_OFFSET_BASIS = 2166136261u

        // Calculate FNV-1a hash
        var hash = FNV_OFFSET_BASIS
        for (char in username) {
            hash = (hash xor char.code.toUInt()) * FNV_PRIME.toUInt()
        }

        val normalizedValue = (hash.toInt() % 3571) / 3571.0
        return normalizedValue
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
