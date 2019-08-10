package com.github.johnnyjayjay.spiglin

import org.bukkit.Bukkit
import org.bukkit.entity.Player
import org.bukkit.potion.PotionEffect
import org.bukkit.potion.PotionEffectType

/**
 * @see Bukkit.getOnlinePlayers
 */
val onlinePlayers: Collection<Player>
    get() = Bukkit.getOnlinePlayers()

/**
 * @see Bukkit.broadcastMessage
 */
fun broadcast(message: String) = Bukkit.broadcastMessage(message)

/**
 * @see PotionEffect constructor
 */
fun effect(
    type: PotionEffectType,
    duration: Int = Int.MAX_VALUE,
    amplifier: Int = 1,
    ambient: Boolean = true,
    particles: Boolean = true,
    icon: Boolean = true
) = PotionEffect(type, duration, amplifier, ambient, particles, icon)
