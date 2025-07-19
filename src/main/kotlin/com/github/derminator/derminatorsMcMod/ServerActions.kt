package com.github.derminator.derminatorsMcMod

import net.minecraft.network.chat.Component
import net.minecraft.world.entity.player.Player
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.Items

fun giveStick(player: Player) {
    player.sendSystemMessage(Component.literal("Welcome to the server; here's a stick!"))
    player.inventory.add(ItemStack(Items.STICK))
}