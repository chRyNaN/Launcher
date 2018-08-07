package com.chrynan.launcher.usecase

import com.chrynan.launcher.logging.Loggable
import com.chrynan.launcher.logging.Logger

abstract class BaseUseCase<T, R> : UseCase<T, R>,
        Loggable by Logger