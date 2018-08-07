package com.chrynan.launcher.ui.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebChromeClient
import android.webkit.WebViewClient
import com.chrynan.launcher.R
import com.chrynan.launcher.navigator.FeedNavigator
import com.chrynan.launcher.presenter.FeedPresenter
import com.chrynan.launcher.ui.view.FeedView
import kotlinx.android.synthetic.main.fragment_feed.*
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

    @SuppressLint("SetJavaScriptEnabled")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        webView?.apply {
            webChromeClient = WebChromeClient()
            webViewClient = WebViewClient()
            settings.javaScriptEnabled = true
        }
    }

    override fun showLoadingIndicator() {
        progressBar?.visibility = View.VISIBLE
    }

    override fun hideLoadingIndicator() {
        progressBar?.visibility = View.GONE
    }

    override fun showList() {
        recyclerView?.visibility = View.VISIBLE
    }

    override fun hideList() {
        recyclerView?.visibility = View.GONE
    }

    override fun showWebsite() {
        webView?.visibility = View.VISIBLE
    }

    override fun hideWebsite() {
        webView?.visibility = View.GONE
    }

    override fun showEmptyFeed() {
        // TODO
    }

    override fun hideEmptyFeed() {
        // TODO
    }

    override fun showError() {
        // TODO
    }

    override fun hideError() {
        // TODO
    }
}