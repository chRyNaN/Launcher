package com.chrynan.launcher.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.chrynan.launcher.R
import com.chrynan.launcher.navigator.FeedNavigator
import com.chrynan.launcher.presenter.FeedPresenter
import com.chrynan.launcher.ui.view.FeedView
import javax.inject.Inject

class FeedFragment : BaseFragment(),
        FeedView,
        FeedNavigator {

    companion object {

        fun newInstance() = FeedFragment()
    }

    @Inject
    lateinit var presenter: FeedPresenter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
            inflater.inflate(R.layout.fragment_feed, container, false)
}