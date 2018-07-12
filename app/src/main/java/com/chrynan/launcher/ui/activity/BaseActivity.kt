package com.chrynan.launcher.ui.activity

import android.support.v4.app.NavUtils
import com.chrynan.launcher.navigator.Navigator
import dagger.android.support.DaggerAppCompatActivity

abstract class BaseActivity : DaggerAppCompatActivity(),
        Navigator {

    override fun onBackPressed() {
        if (supportFragmentManager.backStackEntryCount > 0) {
            supportFragmentManager.popBackStack()
        } else {
            super.onBackPressed()
        }
    }

    override fun goUp() {
        if (NavUtils.getParentActivityName(this) != null) {
            NavUtils.navigateUpFromSameTask(this)
        } else {
            onBackPressed()
        }
    }

    override fun goBack() = onBackPressed()
}