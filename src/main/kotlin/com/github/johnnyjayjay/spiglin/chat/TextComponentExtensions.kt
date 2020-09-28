package com.github.johnnyjayjay.spiglin.chat

import net.md_5.bungee.api.chat.*
import net.md_5.bungee.api.chat.hover.content.Content
import net.md_5.bungee.api.chat.hover.content.Text
import org.bukkit.entity.Player

/**
 * Creates new components with a [ComponentBuilder].
 *
 * @param content the original content of the message or `null`
 * @param block a lambda with receiver applied on the component
 */
public fun buildComponent(content: String? = null, block: ComponentBuilder.() -> Unit): Array<BaseComponent> {
    val builder = content?.let { ComponentBuilder(content) } ?: ComponentBuilder()
    return builder.apply(block).create()
}

/**
 * Convert's this String to a [TextComponent].
 */
public fun String.toComponent(): TextComponent = TextComponent(this)

/**
 * Convert's this String to a [TextComponent] and applies [block] to it.
 */
public fun String.toComponent(block: TextComponent.() -> Unit): TextComponent = toComponent().invoke(block)

/**
 * Utility function that allows using [BaseComponent] in a builder DSL.
 */
public operator fun <T : BaseComponent> T.invoke(block: T.() -> Unit): T = apply(block)

/**
 * Sets [BaseComponent.clickEvent]
 *
 * @param action the [ClickEvent.Action] of this event
 * @param value the value for this event
 */
public fun BaseComponent.clickEvent(action: ClickEvent.Action, value: String) {
    clickEvent = ClickEvent(action, value)
}

/**
 * Sets [BaseComponent.hoverEvent].
 *
 * @param action the [HoverEvent.Action] of this event
 * @param contents a vararg of [Contents](Content) being added to this event
 */
public fun BaseComponent.hoverEvent(action: HoverEvent.Action, vararg contents: Content) {
    hoverEvent = HoverEvent(action, *contents)
}

/**
 * Sets [BaseComponent.hoverEvent].
 *
 * @param action the [HoverEvent.Action] of this event
 * @param contents a vararg of Strings being added to the content of this event
 */
public fun BaseComponent.hoverEvent(action: HoverEvent.Action, vararg contents: String): Unit =
    hoverEvent(action, *contents.map(String::toContent).toTypedArray())

/**
 * @param action the [HoverEvent.Action] of this event
 * @param value the value of the event
 */
public fun ComponentBuilder.clickEvent(action: ClickEvent.Action, value: String) {
    event(ClickEvent(action, value))
}

/**
 * @param action the [HoverEvent.Action] of this event
 * @param contents a vararg of [Contents](Content) being added to this event
 */
public fun ComponentBuilder.hoverEvent(action: HoverEvent.Action, vararg contents: Content) {
    event(HoverEvent(action, *contents))
}

/**
 * @param action the [HoverEvent.Action] of this event
 * @param contents a vararg of Strings being added to the content of this event
 */
public fun ComponentBuilder.hoverEvent(action: HoverEvent.Action, vararg contents: String): Unit =
    hoverEvent(action, *contents.map(String::toContent).toTypedArray())

/**
 * Converts this String to a [Content].
 */
public fun String.toContent(): Content = Text(this)

/**
 * Sends the [component] to this player.
 */
public fun Player.sendMessage(component: BaseComponent): Unit = spigot().sendMessage(component)

/**
 * Sends a [TextComponent] message to this player.
 *
 * @param block a lambda with received being applied to the [TextComponent] before sending
 */
public fun Player.sendMessage(content: String, block: TextComponent.() -> Unit): Unit =
    sendMessage(content.toComponent(block))

/**
 * Sends every component from [components] to the player.
 *
 * @see sendMessage
 */
public fun Player.sendMessage(components: Array<BaseComponent>): Unit =
    components.forEach(::sendMessage)

/**
 * Builds a message using a [ComponentBuilder].
 *
 * @param content the original content of the message or `null`
 * @param block a lambda with receiver applied on the component
 */
public fun Player.createMessage(content: String? = null, block: ComponentBuilder.() -> Unit): Unit = sendMessage(
    buildComponent(content, block)
)
