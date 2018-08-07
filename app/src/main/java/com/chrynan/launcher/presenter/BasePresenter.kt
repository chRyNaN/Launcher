package com.chrynan.launcher.presenter

import com.chrynan.launcher.logging.Loggable
import com.chrynan.launcher.logging.Logger
import com.chrynan.launcher.resources.ResourceFetcher
import com.chrynan.launcher.resources.ResourceProvider

abstract class BasePresenter : Presenter,
        Loggable by Logger,
        ResourceFetcher by ResourceProvider