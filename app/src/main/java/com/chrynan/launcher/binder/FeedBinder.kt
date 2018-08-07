package com.chrynan.launcher.binder

import com.chrynan.launcher.model.state.FeedState
import com.chrynan.launcher.ui.view.FeedView
import com.chrynan.launcher.util.perform
import javax.inject.Inject

class FeedBinder @Inject constructor(override val view: FeedView) : Binder<FeedState, FeedView> {

    override fun bind(stateModel: FeedState) =
            when (stateModel) {
                FeedState.LoadingWebsite -> view.perform {
                    showLoadingIndicator()
                    showWebsite()
                    hideEmptyFeed()
                    hideError()
                    hideList()
                }
                FeedState.LoadingList -> view.perform {
                    showLoadingIndicator()
                    showList()
                    hideEmptyFeed()
                    hideWebsite()
                    hideError()
                }
                FeedState.EmptyState -> view.perform {
                    showEmptyFeed()
                    hideLoadingIndicator()
                    hideWebsite()
                    hideList()
                    hideError()
                }
                FeedState.ListState -> view.perform {
                    showList()
                    hideLoadingIndicator()
                    hideWebsite()
                    hideEmptyFeed()
                    hideError()
                }
                FeedState.WebsiteState -> view.perform {
                    showWebsite()
                    hideLoadingIndicator()
                    hideWebsite()
                    hideList()
                    hideError()
                }
                FeedState.ErrorState -> view.perform {
                    showError()
                    hideLoadingIndicator()
                    hideWebsite()
                    hideList()
                    hideEmptyFeed()
                }
            }
}