package com.chrynan.launcher.usecase

interface NoParamUseCase<R> : UseCase<Nothing?, R> {

    fun execute() = execute(null)
}