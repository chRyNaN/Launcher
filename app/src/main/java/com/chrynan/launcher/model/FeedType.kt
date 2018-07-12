package com.chrynan.launcher.model

sealed class FeedType {

    object None : FeedType()

    data class WebSite(val url: String) : FeedType()

    object AppFeed : FeedType()
}