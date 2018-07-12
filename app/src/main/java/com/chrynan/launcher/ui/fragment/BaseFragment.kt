package com.chrynan.launcher.ui.fragment

import com.chrynan.launcher.navigator.Navigator
import dagger.android.support.DaggerFragment

abstract class BaseFragment : DaggerFragment(),
        Navigator {

    override fun goUp() {
        (activity as? Navigator)?.goUp()
    }

    override fun goBack() {
        (activity as? Navigator)?.goBack()
    }
}