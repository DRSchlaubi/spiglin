package com.github.johnnyjayjay.spiglin

import org.bukkit.util.Vector
import kotlin.math.pow
import kotlin.math.sqrt

/** The Euclidian Norm of this vector. */
public val Vector.abs: Double
    get() = sqrt(x.pow(2) + y.pow(2) + z.pow(2))

/** Returns a vector with x, y, z from this vector negated. */
public operator fun Vector.unaryMinus(): Vector =
    clone().apply { x = -x; y = -y; z = -z; }

/** Returns an unchanged copy of this vector. */
public operator fun Vector.unaryPlus(): Vector =
    clone()

/** Compares two vectors based on their Euclidian Norm (absolute value) */
public operator fun Vector.compareTo(other: Vector): Int =
    this.abs.compareTo(other.abs)

/** Returns a vector that is the result of this vector multiplied with the given scalar. */
public operator fun Vector.times(scalar: Number): Vector =
    clone().multiply(scalar.toDouble())

/** Multiplies this vector with the given scalar. */
public operator fun Vector.timesAssign(scalar: Number) {
    multiply(scalar.toDouble())
}

/** Returns a vector that is the result of this vector plus the given vector. */
public operator fun Vector.plus(vector: Vector): Vector =
    clone().add(vector)

/** Adds the given vector to this vector */
public operator fun Vector.plusAssign(vector: Vector) {
    add(vector)
}

/** Returns a vector that is the result of this vector minus the given vector. */
public operator fun Vector.minus(vector: Vector): Vector =
    clone().subtract(vector)

/** Subtracts the given vector from this vector */
public operator fun Vector.minusAssign(vector: Vector) {
    subtract(vector)
}

/** Returns the dot product of this and another vector. */
public operator fun Vector.times(vector: Vector): Double =
    clone().dot(vector)

/** Returns the cross product of this and another vector. */
public infix fun Vector.x(vector: Vector): Vector =
    clone().crossProduct(vector)
