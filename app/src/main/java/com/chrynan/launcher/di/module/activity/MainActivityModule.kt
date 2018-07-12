package com.chrynan.launcher.di.module.activity

import android.support.v4.app.FragmentManager
import com.chrynan.launcher.di.module.fragment.AppListFragmentModule
import com.chrynan.launcher.di.module.fragment.FeedFragmentModule
import com.chrynan.launcher.di.module.fragment.HomeFragmentModule
import com.chrynan.launcher.di.scope.ActivityScope
import com.chrynan.launcher.di.scope.FragmentScope
import com.chrynan.launcher.navigator.MainNavigator
import com.chrynan.launcher.ui.activity.MainActivity
import com.chrynan.launcher.ui.fragment.AppListFragment
import com.chrynan.launcher.ui.fragment.FeedFragment
import com.chrynan.launcher.ui.fragment.HomeFragment
import com.chrynan.launcher.ui.view.MainView
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.android.ContributesAndroidInjector

@Module
internal abstract class MainActivityModule {

    @Module
    companion object {

        @Provides
        @ActivityScope
        @JvmStatic
        fun provideFragmentManager(activity: MainActivity): FragmentManager = activity.supportFragmentManager
    }

    @Binds
    @ActivityScope
    abstract fun bindMainView(activity: MainActivity): MainView

    @Binds
    @ActivityScope
    abstract fun bindMainNavigator(activity: MainActivity): MainNavigator

    @FragmentScope
    @ContributesAndroidInjector(modules = [FeedFragmentModule::class])
    abstract fun feedFragmentInjector(): FeedFragment

    @FragmentScope
    @ContributesAndroidInjector(modules = [HomeFragmentModule::class])
    abstract fun homeFragmentInjector(): HomeFragment

    @FragmentScope
    @ContributesAndroidInjector(modules = [AppListFragmentModule::class])
    abstract fun appListFragmentInjector(): AppListFragment
}