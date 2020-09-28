package com.github.johnnyjayjay.spiglin

import org.bukkit.Location
import org.bukkit.util.Vector

/** Returns a Location that is the result of this location plus the given vector */
public operator fun Location.plus(vector: Vector): Location =
    clone().add(vector)

/** Adds the given vector to this location */
public operator fun Location.plusAssign(vector: Vector) {
    add(vector)
}

/** Returns a Location that is the result of this location minus the given vector */
public operator fun Location.minus(vector: Vector): Location =
    clone().subtract(vector)

/** Subtracts the given vector from this location */
public operator fun Location.minusAssign(vector: Vector) {
    subtract(vector)
}
