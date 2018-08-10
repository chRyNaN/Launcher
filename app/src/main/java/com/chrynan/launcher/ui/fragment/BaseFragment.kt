package com.chrynan.launcher.ui.fragment

import com.chrynan.launcher.logging.Loggable
import com.chrynan.launcher.logging.Logger
import com.chrynan.launcher.navigator.Navigator
import com.chrynan.launcher.resources.ResourceFetcher
import com.chrynan.launcher.resources.ResourceProvider
import dagger.android.support.DaggerFragment

abstract class BaseFragment : DaggerFragment(),
        Navigator,
        Loggable by Logger,
        ResourceFetcher by ResourceProvider {

    override fun goUp() {
        (activity as? Navigator)?.goUp()
    }

    override fun goBack() {
        (activity as? Navigator)?.goBack()
    }
}