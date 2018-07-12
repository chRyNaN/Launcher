package com.chrynan.launcher.mapper

import com.chrynan.launcher.model.AppListViewModel
import com.chrynan.launcher.model.LauncherItem
import javax.inject.Inject

class AppListViewModelMapper @Inject constructor() : Mapper<LauncherItem.SingleItem.App, AppListViewModel> {

    override fun map(model: LauncherItem.SingleItem.App) = AppListViewModel(app = model)
}