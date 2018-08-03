package com.chrynan.launcher.binder

import com.chrynan.launcher.model.*
import com.chrynan.launcher.ui.view.AppListView
import javax.inject.Inject

class AppListBinder @Inject constructor(override val view: AppListView) : Binder<AppListState, AppListView> {

    override fun bind(stateModel: AppListState) =
            when (stateModel) {
                LoadingState -> {
                    view.showLoadingIndicator()
                    view.hideList()
                    view.hideNoApps()
                    view.hideNoResults()
                    view.hideInput()
                }
                ListState -> {
                    view.showList()
                    view.showInput()
                    view.hideLoadingIndicator()
                    view.hideNoApps()
                    view.hideNoResults()
                }
                NoResultsState -> {
                    view.showNoResults()
                    view.showInput()
                    view.hideLoadingIndicator()
                    view.hideNoApps()
                    view.hideList()
                }
                NoAppsState -> {
                    view.showNoApps()
                    view.hideLoadingIndicator()
                    view.hideList()
                    view.hideNoResults()
                    view.hideInput()
                }
            }
}