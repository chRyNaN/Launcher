package com.chrynan.launcher.presenter

import com.chrynan.launcher.resources.ResourceFetcher
import com.chrynan.launcher.resources.ResourceProvider

abstract class BasePresenter : Presenter,
        ResourceFetcher by ResourceProvider