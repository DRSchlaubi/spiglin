package com.github.johnnyjayjay.spiglin.scheduler

import org.bukkit.plugin.Plugin
import org.bukkit.scheduler.BukkitRunnable
import org.bukkit.scheduler.BukkitTask

/**
 * Runs a task using [org.bukkit.scheduler.BukkitRunnable.runTask] or its asynchronous equivalent.
 *
 * @param async Whether this task should be run asynchronously (true) or on the Bukkit thread (false).
 *              Default: false
 * @param task A function with a [BukkitRunnable] as its receiver that will be executed once when this is run.
 * @see org.bukkit.scheduler.BukkitRunnable.runTask
 * @see org.bukkit.scheduler.BukkitRunnable.runTaskAsynchronously
 */
public fun Plugin.run(async: Boolean = false, task: BukkitRunnable.() -> Unit): BukkitTask {
    val runnable = DelegatingRunnable(task)
    return if (async) {
        runnable.runTaskAsynchronously(this)
    } else {
        runnable.runTask(this)
    }
}

/**
 * Runs a task using [org.bukkit.scheduler.BukkitRunnable.runTaskLater] or its asynchronous equivalent.
 *
 * @param ticks For how many ticks this task should be delayed.
 * @param async Whether this task should be run asynchronously (true) or on the Bukkit thread (false).
 *              Default: false
 * @param task A function with a [BukkitRunnable] as its receiver that will be executed once when this is run.
 * @see org.bukkit.scheduler.BukkitRunnable.runTaskLater
 * @see org.bukkit.scheduler.BukkitRunnable.runTaskLaterAsynchronously
 */
public fun Plugin.delay(ticks: Long, async: Boolean = false, task: BukkitRunnable.() -> Unit): BukkitTask {
    val runnable = DelegatingRunnable(task)
    return if (async) {
        runnable.runTaskLaterAsynchronously(this, ticks)
    } else {
        runnable.runTaskLater(this, ticks)
    }
}

/**
 * Runs a task using [org.bukkit.scheduler.BukkitRunnable.runTaskTimer] or its asynchronous equivalent.
 *
 * @param delay For how many ticks this task should be delayed the first time. Default: 0
 * @param period The period in ticks in which this task should be executed. Default: 20
 * @param async Whether this task should be run asynchronously (true) or on the Bukkit thread (false).
 *              Default: false
 * @param task A function with a [BukkitRunnable] as its receiver that will be
 *             executed the first time after the given delay, and then each time the given period passes.
 * @see org.bukkit.scheduler.BukkitRunnable.runTaskTimer
 * @see org.bukkit.scheduler.BukkitRunnable.runTaskTimerAsynchronously
 */
public fun Plugin.schedule(
    delay: Long = 0,
    period: Long = 20,
    async: Boolean = false,
    task: BukkitRunnable.() -> Unit
): BukkitTask {
    val runnable = DelegatingRunnable(task)
    return if (async) {
        runnable.runTaskTimerAsynchronously(this, delay, period)
    } else {
        runnable.runTaskTimer(this, delay, period)
    }
}

/**
 * Repeats a task for each element in the given progression.
 *
 * @param progression The progression to follow, e.g. an [IntRange].
 * @param delay For how many ticks this task should be delayed the first time. Default: 0
 * @param period The period in ticks in which this task should be executed. Default: 20
 * @param async Whether this task should be run asynchronously (true) or on the Bukkit thread (false).
 *              Default: false
 * @param task A function with a [BukkitRunnable] as its receiver that will be
 *             executed the first time after the given delay, and then each time the given period passes
 *             and as long as there are elements left to progress. The parameter represents the current
 *             element in this progression.
 */
public inline fun Plugin.repeat(
    progression: IntProgression,
    delay: Long = 0,
    period: Long = 20,
    async: Boolean = false,
    crossinline task: BukkitRunnable.(Int) -> Unit
): BukkitTask {
    val runnable = object : BukkitRunnable() {
        private val iterator = progression.iterator()

        override fun run() {
            if (iterator.hasNext()) {
                task(iterator.nextInt())
            } else {
                cancel()
            }
        }
    }

    return if (async) {
        runnable.runTaskTimerAsynchronously(this, delay, period)
    } else {
        runnable.runTaskTimer(this, delay, period)
    }
}

internal class DelegatingRunnable(private val task: BukkitRunnable.() -> Unit) : BukkitRunnable() {
    override fun run() = task()
}
