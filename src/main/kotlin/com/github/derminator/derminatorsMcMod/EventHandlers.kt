package com.github.derminator.derminatorsMcMod

import net.minecraft.network.chat.Component
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.Items
import net.neoforged.bus.api.SubscribeEvent
import net.neoforged.neoforge.event.entity.player.PlayerEvent

object EventHandlers {
    @SubscribeEvent
    fun onPlayerJoin(event: PlayerEvent.PlayerLoggedInEvent) {
        val player = event.entity

        // Send welcome message
        player.sendSystemMessage(Component.literal("Welcome to the server; here's a stick!"))

        // Add a stick to player's inventory
        player.inventory.add(ItemStack(Items.STICK))
    }
}
