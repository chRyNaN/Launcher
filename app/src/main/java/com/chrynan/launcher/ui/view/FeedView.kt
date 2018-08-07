package com.chrynan.launcher.ui.view

interface FeedView {

    fun showLoadingIndicator()

    fun hideLoadingIndicator()

    fun showList()

    fun hideList()

    fun showWebsite()

    fun hideWebsite()

    fun showEmptyFeed()

    fun hideEmptyFeed()

    fun showError()

    fun hideError()
}