package com.chrynan.launcher.di.module.fragment

import com.chrynan.launcher.di.scope.FragmentScope
import com.chrynan.launcher.navigator.HomeNavigator
import com.chrynan.launcher.ui.fragment.HomeFragment
import com.chrynan.launcher.ui.view.HomeView
import dagger.Binds
import dagger.Module

@Module
internal abstract class HomeFragmentModule {

    @Binds
    @FragmentScope
    abstract fun bindHomeView(fragment: HomeFragment): HomeView

    @Binds
    @FragmentScope
    abstract fun bindHomeNavigator(fragment: HomeFragment): HomeNavigator
}