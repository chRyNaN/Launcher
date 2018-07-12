package com.chrynan.launcher.ui.view

import com.chrynan.launcher.model.AppListState

interface AppListView {

    fun updateState(state: AppListState)
}