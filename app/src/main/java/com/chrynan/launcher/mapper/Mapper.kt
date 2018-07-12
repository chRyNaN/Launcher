package com.chrynan.launcher.mapper

interface Mapper<T, R> {

    fun map(model: T): R
}