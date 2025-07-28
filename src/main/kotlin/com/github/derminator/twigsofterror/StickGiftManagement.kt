package com.github.derminator.twigsofterror

import net.minecraft.network.chat.Component
import net.minecraft.world.entity.player.Player
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.Items
import org.slf4j.LoggerFactory
import kotlin.random.Random
import kotlin.time.Duration
import kotlin.time.Duration.Companion.hours
import kotlin.time.Duration.Companion.minutes
import kotlin.time.TimeSource
import kotlin.time.TimeSource.Monotonic.markNow

private fun giveStick(player: Player) {
    player.sendSystemMessage(Component.literal("Welcome to the server; here's a stick!"))
    player.inventory.add(ItemStack(Items.STICK))
}

private val playersNeedingStick = mutableMapOf<String, TimeSource.Monotonic.ValueTimeMark>()
private val LOGGER = LoggerFactory.getLogger("Stick Gift Management")

private val MIN_WAIT = 5.minutes
private val MAX_WAIT = 5.hours
private val WAIT_RANGE = MAX_WAIT - MIN_WAIT
private const val MAX_DITHER_HOURS = 1.0
private const val MIN_DITHER_HOURS = -MAX_DITHER_HOURS

/**
 * Determines how many ticks between stick gifts a player should wait based on their username.
 * Results range from 5 minutes to 5 hours.
 *
 * @param username The player's username
 * @return Number of ticks to wait before giving another stick
 */
private fun calculateStickWaitTime(username: String): Duration {
    val normalizedValue = 1 - getUserEventProbability(username)

    // Calculate ticks between MIN_WAIT_TICKS and MAX_WAIT_TICKS
    val waitTime = MIN_WAIT + WAIT_RANGE * normalizedValue + Random.nextDouble(MIN_DITHER_HOURS, 1.0).hours

    // Ensure the result is within bounds
    return waitTime.coerceIn(MIN_WAIT, MAX_WAIT).also {
        LOGGER.info("$username will get another stick in $it")
    }
}

internal fun giveStickIfNeeded(player: Player) {
    val stickTime = playersNeedingStick[player.name.string] ?: markNow()
    if (stickTime.hasPassedNow()) {
        giveStick(player)
        playersNeedingStick[player.name.string] = markNow() + calculateStickWaitTime(player.name.string)
    }
}