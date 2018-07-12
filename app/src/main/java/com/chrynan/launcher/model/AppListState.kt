package com.chrynan.launcher.model

sealed class AppListState(
        val showList: Boolean,
        val showInput: Boolean,
        val showNoResults: Boolean,
        val isLoading: Boolean
) {

    val showNoApps: Boolean
        get() = !showList
}

object LoadingState : AppListState(showList = false, showInput = false, showNoResults = false, isLoading = true)

object ListState : AppListState(showList = true, showInput = true, showNoResults = false, isLoading = false)

object NoAppsState : AppListState(showList = false, showInput = false, showNoResults = false, isLoading = false)

object NoResultsState : AppListState(showList = false, showInput = true, showNoResults = true, isLoading = false)