package com.chrynan.launcher.di.module.fragment

import com.chrynan.launcher.di.scope.FragmentScope
import com.chrynan.launcher.navigator.FeedNavigator
import com.chrynan.launcher.ui.fragment.FeedFragment
import com.chrynan.launcher.ui.view.FeedView
import dagger.Binds
import dagger.Module

@Module
internal abstract class FeedFragmentModule {

    @Binds
    @FragmentScope
    abstract fun bindFeedView(fragment: FeedFragment): FeedView

    @Binds
    @FragmentScope
    abstract fun bindFeedNavigator(fragment: FeedFragment): FeedNavigator
}