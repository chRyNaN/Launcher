package com.chrynan.launcher.di.module

import com.chrynan.launcher.di.module.activity.MainActivityModule
import com.chrynan.launcher.di.scope.ActivityScope
import com.chrynan.launcher.ui.activity.MainActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
internal abstract class ActivityBuilder {

    @ActivityScope
    @ContributesAndroidInjector(modules = [MainActivityModule::class])
    abstract fun mainActivityInjector(): MainActivity
}