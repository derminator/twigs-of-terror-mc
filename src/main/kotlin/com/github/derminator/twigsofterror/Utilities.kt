package com.github.derminator.twigsofterror

// FNV-1a constants for 32-bit hash
private const val FNV_PRIME = 16777619
private const val FNV_OFFSET_BASIS = 2166136261u

/**
 * Generate the base probability that events should occur for a user based on their username
 *
 * @param username user to generate probability for
 * @return base probability for events [0-1]
 */
internal fun getUserEventProbability(username: String): Double {
    // Calculate FNV-1a hash
    var hash = FNV_OFFSET_BASIS
    for (char in username) {
        hash = (hash xor char.code.toUInt()) * FNV_PRIME.toUInt()
    }

    val normalizedValue = (hash.toInt() % 3571) / 3571.0
    return 1 - normalizedValue
}