package com.chrynan.launcher.util

import java.text.Collator

fun emptyString() = ""

fun <T> Iterable<T>.sortAlphabeticallyBy(selector: (T) -> String): List<T> =
        Collator.getInstance().let {
            sortedWith(Comparator { a, b -> it.compare(selector(a), selector(b)) })
        }