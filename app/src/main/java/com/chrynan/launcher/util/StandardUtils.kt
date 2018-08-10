package com.chrynan.launcher.util

fun <T> T.perform(block: T.() -> Unit) = block()

fun Unit?.orUnit() = this ?: Unit