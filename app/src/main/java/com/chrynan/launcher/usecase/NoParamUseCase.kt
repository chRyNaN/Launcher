package com.chrynan.launcher.usecase

import com.chrynan.launcher.logging.Loggable
import com.chrynan.launcher.logging.Logger

abstract class NoParamUseCase<R> : UseCase<Nothing?, R>,
        Loggable by Logger {

    fun execute() = execute(null)
}