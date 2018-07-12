package com.chrynan.launcher.di.module.fragment

import com.chrynan.launcher.di.scope.FragmentScope
import com.chrynan.launcher.navigator.AppListNavigator
import com.chrynan.launcher.ui.adapter.AppListAdapter
import com.chrynan.launcher.ui.adapter.AppListSearchAdapter
import com.chrynan.launcher.ui.adapter.core.ListUpdater
import com.chrynan.launcher.ui.adapter.core.ManagerAdapter
import com.chrynan.launcher.ui.fragment.AppListFragment
import com.chrynan.launcher.ui.view.AppListView
import dagger.Binds
import dagger.Module
import dagger.Provides

@Module
internal abstract class AppListFragmentModule {

    @Module
    companion object {

        @JvmStatic
        @Provides
        @FragmentScope
        fun provideAdapter(appAdapter: AppListAdapter, searchButtonAdapter: AppListSearchAdapter): ManagerAdapter =
                ManagerAdapter(setOf(appAdapter, searchButtonAdapter)).apply { setHasStableIds(true) }
    }

    @Binds
    @FragmentScope
    abstract fun bindAppListView(fragment: AppListFragment): AppListView

    @Binds
    @FragmentScope
    abstract fun bindAppListNavigator(fragment: AppListFragment): AppListNavigator

    @Binds
    @FragmentScope
    abstract fun bindListUpdater(adapter: ManagerAdapter): ListUpdater

    @Binds
    @FragmentScope
    abstract fun bindAppIconClickListener(fragment: AppListFragment): AppListAdapter.ClickListener

    @Binds
    @FragmentScope
    abstract fun bindSearchButtonClickListener(fragment: AppListFragment): AppListSearchAdapter.ClickListener
}