package com.chrynan.launcher.ui.activity

import android.os.Bundle
import android.view.View
import com.chrynan.launcher.R
import com.chrynan.launcher.navigator.MainNavigator
import com.chrynan.launcher.presenter.MainPresenter
import com.chrynan.launcher.ui.adapter.pager.MainPagerAdapter
import com.chrynan.launcher.ui.view.MainView
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject

class MainActivity : BaseActivity(),
        MainView,
        MainNavigator {

    companion object {

        private const val FEED_FRAGMENT_POSITION = 0
        private const val HOME_FRAGMENT_POSITION = 1
    }

    @Inject
    lateinit var presenter: MainPresenter

    @Inject
    lateinit var adapter: MainPagerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setupWindow()

        viewPager?.adapter = adapter

        goToHome()
    }

    override fun goToFeed() {
        viewPager?.setCurrentItem(FEED_FRAGMENT_POSITION, true)
    }

    override fun goToHome() {
        viewPager?.setCurrentItem(HOME_FRAGMENT_POSITION, true)
    }

    private fun setupWindow() {
        window.decorView.systemUiVisibility =
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
    }
}