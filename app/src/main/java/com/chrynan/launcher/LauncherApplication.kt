package com.chrynan.launcher

import com.chrynan.launcher.di.component.DaggerAppComponent
import com.chrynan.launcher.logging.Logger
import com.chrynan.launcher.resources.ResourceProvider
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication

@Suppress("unused")
class LauncherApplication : DaggerApplication() {

    override fun onCreate() {
        super.onCreate()

        Logger.init()
        ResourceProvider.application = this
    }

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> =
            DaggerAppComponent.builder().application(this).build()
}