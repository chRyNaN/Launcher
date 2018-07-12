package com.chrynan.launcher.usecase

interface UseCase<T, R> {

    fun execute(param: T): R
}