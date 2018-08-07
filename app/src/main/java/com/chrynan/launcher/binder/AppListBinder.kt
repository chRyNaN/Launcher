package com.chrynan.launcher.binder

import com.chrynan.launcher.model.state.AppListState
import com.chrynan.launcher.ui.view.AppListView
import com.chrynan.launcher.util.perform
import javax.inject.Inject

class AppListBinder @Inject constructor(override val view: AppListView) : Binder<AppListState, AppListView> {

    override fun bind(stateModel: AppListState) =
            when (stateModel) {
                AppListState.LoadingState -> view.perform {
                    showLoadingIndicator()
                    hideList()
                    hideNoApps()
                    hideNoResults()
                    hideInput()
                }
                AppListState.ListState -> view.perform {
                    showList()
                    showInput()
                    hideLoadingIndicator()
                    hideNoApps()
                    hideNoResults()
                }
                AppListState.NoResultsState -> view.perform {
                    showNoResults()
                    showInput()
                    hideLoadingIndicator()
                    hideNoApps()
                    hideList()
                }
                AppListState.NoAppsState -> view.perform {
                    showNoApps()
                    hideLoadingIndicator()
                    hideList()
                    hideNoResults()
                    hideInput()
                }
            }
}