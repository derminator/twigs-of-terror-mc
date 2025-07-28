package com.github.derminator.twigsofterror

import net.neoforged.fml.common.Mod
import net.neoforged.neoforge.common.NeoForge

/**
 * Main mod class.
 */
@Mod(TwigsOfTerror.ID)
object TwigsOfTerror {
    const val ID = "twigs_of_terror"

    init {
        NeoForge.EVENT_BUS.register(EventHandlers)
    }
}