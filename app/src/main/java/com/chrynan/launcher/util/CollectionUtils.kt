package com.chrynan.launcher.util

fun <T : Any> setOfNotNull(vararg elements: T?) = listOfNotNull(*elements).toSet()
