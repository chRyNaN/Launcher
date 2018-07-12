package com.chrynan.launcher.presenter

import com.chrynan.launcher.logging.Logger
import javax.inject.Inject

class MainPresenter @Inject constructor(
        private val logger: Logger
) : Presenter {

    override fun detach() {
        // No-Op
    }
}