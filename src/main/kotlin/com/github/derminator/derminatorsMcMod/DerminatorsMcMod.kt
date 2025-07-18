package com.github.derminator.derminatorsMcMod

import net.neoforged.fml.common.Mod
import net.neoforged.neoforge.common.NeoForge

/**
 * Main mod class.
 */
@Mod(DerminatorsMcMod.ID)
object DerminatorsMcMod {
    const val ID = "derminators_mc_mod"

    init {
        NeoForge.EVENT_BUS.register(ServerEvents)
    }
}
