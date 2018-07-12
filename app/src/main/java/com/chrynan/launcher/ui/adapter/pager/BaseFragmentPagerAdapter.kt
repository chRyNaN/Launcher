package com.chrynan.launcher.ui.adapter.pager

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter

open class BaseFragmentPagerAdapter(
        fragmentManager: FragmentManager,
        private val fragments: List<Fragment>
) : FragmentPagerAdapter(fragmentManager) {

    override fun getItem(position: Int) = fragments[position]

    override fun getCount() = fragments.size
}