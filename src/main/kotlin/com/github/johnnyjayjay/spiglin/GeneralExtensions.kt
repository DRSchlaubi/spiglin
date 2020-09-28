package com.github.johnnyjayjay.spiglin

import org.bukkit.Bukkit
import org.bukkit.Server
import org.bukkit.entity.Player
import org.bukkit.plugin.PluginManager

/**
 * @see Bukkit.getOnlinePlayers
 */
public val onlinePlayers: Collection<Player>
    get() = Bukkit.getOnlinePlayers()

/**
 * @see Bukkit.broadcastMessage
 */
public fun broadcast(message: String): Int = Bukkit.broadcastMessage(message)

/**
 * A singleton that delegates to [Bukkit.getServer]
 */
public object Server : Server by Bukkit.getServer()

/**
 * A singleton that delegates to [Bukkit.getPluginManager]
 */
public object PluginManager : PluginManager by Bukkit.getPluginManager()
