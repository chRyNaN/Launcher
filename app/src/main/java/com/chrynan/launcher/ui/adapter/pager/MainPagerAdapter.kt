package com.chrynan.launcher.ui.adapter.pager

import android.support.v4.app.FragmentManager
import com.chrynan.launcher.ui.fragment.FeedFragment
import com.chrynan.launcher.ui.fragment.HomeFragment
import javax.inject.Inject

class MainPagerAdapter @Inject constructor(fragmentManager: FragmentManager) : BaseFragmentPagerAdapter(
        fragmentManager,
        listOf(FeedFragment.newInstance(), HomeFragment.newInstance())
)