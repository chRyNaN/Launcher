package com.chrynan.launcher

import com.chrynan.launcher.di.component.DaggerAppComponent
import com.chrynan.launcher.logging.Logger
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication
import javax.inject.Inject

@Suppress("unused")
class LauncherApplication : DaggerApplication() {

    @Inject
    lateinit var logger: Logger

    override fun onCreate() {
        super.onCreate()

        logger.init()
    }

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> =
            DaggerAppComponent.builder().application(this).build()
}