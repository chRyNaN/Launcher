package com.chrynan.launcher.navigator

import android.content.Intent

interface AppListNavigator : Navigator {

    fun goToApp(launchIntent: Intent)

    fun goToGooglePlayStoreWithSearch(search: String)
}