package com.chrynan.launcher.util

fun <T> T.perform(block: T.() -> Unit) = block()