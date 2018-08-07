package com.chrynan.launcher.model.state

sealed class FeedState {

    object LoadingWebsite : FeedState()

    object LoadingList : FeedState()

    object EmptyState : FeedState()

    object ListState : FeedState()

    object WebsiteState : FeedState()

    object ErrorState : FeedState()
}