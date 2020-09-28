package com.github.johnnyjayjay.spiglin.item

import org.bukkit.enchantments.Enchantment

/**
 * A class used to apply enchantments to ItemMetas and ItemStacks.
 *
 * @see org.bukkit.inventory.ItemStack.enchant
 * @see org.bukkit.inventory.meta.ItemMeta.enchant
 */
public class EnchantmentNode {

    private val _set: MutableSet<EnchantmentContainer> = mutableSetOf()

    /**
     * A [Set] containing the [EnchantmentContainer]s created within this node.
     */
    public val set: Set<EnchantmentContainer> = _set

    /**
     * Adds an enchantment level 1 to this node and returns the newly
     * created EnchantmentContainer (which can be used to manipulate the level).
     */
    public fun with(enchantment: Enchantment): EnchantmentContainer =
        EnchantmentContainer(enchantment).also { _set.add(it) }

    /**
     * Adds the given enchantments to this node and runs the config function for each.
     */
    public inline fun with(vararg enchantments: Enchantment, config: EnchantmentContainer.(Enchantment) -> Unit) {
        enchantments.forEach {
            with(it).apply { config(it) }
        }
    }

    /**
     * Adds the given map of enchantments and their corresponding level to this node.
     */
    public fun with(enchantments: Map<Enchantment, Int>) {
        _set.addAll(enchantments.map { EnchantmentContainer(it.key, it.value) })
    }
}

/**
 * A data class to represent an [Enchantment] with a level.
 *
 * @property enchantment the type of [Enchantment]
 * @property level the level of [enchantment]
 */
public data class EnchantmentContainer internal constructor(val enchantment: Enchantment, var level: Int = 1) {

    /**
     * Sets the level for this EnchantmentContainer.
     */
    @Suppress("MemberVisibilityCanBePrivate") // end user usage is exoected
    public infix fun level(level: Int) {
        this.level = level
    }
}
