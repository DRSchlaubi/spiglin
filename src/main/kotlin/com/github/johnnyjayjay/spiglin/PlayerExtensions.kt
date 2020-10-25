package com.github.johnnyjayjay.spiglin

import org.bukkit.*
import org.bukkit.entity.Damageable
import org.bukkit.entity.Player
import org.bukkit.plugin.Plugin

/** Sets this Damageable's health to 0. */
public fun Damageable.kill() {
    health = 0.0
}

/** Sets this Damageable's health to maxHealth. */
@Suppress("DEPRECATION")
public fun Damageable.heal() {
    health = maxHealth
}

/** Sets this player's food level to 20. */
public fun Player.feed() {
    foodLevel = 20
}

/** Shows this player to the given players */
@Suppress("DEPRECATION")
public fun Player.showTo(vararg players: Player, plugin: Plugin? = null): Unit =
    players.forEach { if (plugin != null) it.showPlayer(plugin, this) else it.showPlayer(this) }

/** Shows this player to the given players */
@Suppress("DEPRECATION")
public fun Player.showTo(players: Iterable<Player>, plugin: Plugin? = null): Unit =
    players.forEach { if (plugin != null) it.showPlayer(plugin, this) else it.showPlayer(this) }

/** Shows this player to all [onlinePlayers] that match the given predicate. */
@Suppress("DEPRECATION")
public fun Player.showIf(predicate: (Player) -> Boolean, plugin: Plugin? = null) {
    onlinePlayers.asSequence()
        .filter(predicate)
        .forEach { if (plugin != null) it.showPlayer(plugin, this) else it.showPlayer(this) }
}

/** Shows this player to all [onlinePlayers]. */
public fun Player.showToAll(): Unit =
    showTo(onlinePlayers)

/** Hides this player from the given players.*/
@Suppress("DEPRECATION")
public fun Player.hideFrom(vararg players: Player, plugin: Plugin? = null): Unit =
    players.forEach { if (plugin != null) it.hidePlayer(plugin, this) else it.hidePlayer(this) }

/** Hides this player from the given players. */
@Suppress("DEPRECATION")
public fun Player.hideFrom(players: Iterable<Player>, plugin: Plugin? = null): Unit =
    players.forEach { if (plugin != null) it.hidePlayer(plugin, this) else it.hidePlayer(this) }

/** Hides this player from all [onlinePlayers] that match the given predicate. */
@Suppress("DEPRECATION")
public fun Player.hideIf(predicate: (Player) -> Boolean, plugin: Plugin? = null) {
    onlinePlayers.asSequence()
        .filter(predicate)
        .forEach { if (plugin != null) it.hidePlayer(plugin, this) else it.hidePlayer(this) }
}

/** Hides this player from all [onlinePlayers]. */
public fun Player.hideFromAll(): Unit =
    hideFrom(onlinePlayers)

/**
 * @see Player.playEffect
 */
public fun <T> Player.play(location: Location = this.location, effect: Effect, data: T? = null): Unit =
    playEffect(location, effect, data)

/**
 * @see Player.playSound
 */
public fun Player.play(
    location: Location = this.location,
    sound: Sound,
    category: SoundCategory,
    volume: Float = 1F,
    pitch: Float = 1F
): Unit = playSound(location, sound, category, volume, pitch)

/**
 * @see Player.playNote
 */
public fun Player.play(location: Location = this.location, instrument: Instrument, note: Note): Unit =
    playNote(location, instrument, note)




