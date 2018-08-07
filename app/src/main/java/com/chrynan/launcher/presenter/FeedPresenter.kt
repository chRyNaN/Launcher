package com.chrynan.launcher.presenter

import com.chrynan.launcher.binder.FeedBinder
import javax.inject.Inject

class FeedPresenter @Inject constructor(private val binder: FeedBinder) : Presenter {

    override fun detach() {
        // No-Op
    }

    fun updateFeed() {
        // TODO
    }
}