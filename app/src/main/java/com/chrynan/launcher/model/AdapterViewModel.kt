package com.chrynan.launcher.model

sealed class AdapterViewModel(val uniqueId: Long)

data class AppListViewModel(
        val app: LauncherItem.SingleItem.App
) : AdapterViewModel(app.packageName.hashCode().toLong())

data class AppListSearchViewModel(val text: String) : AdapterViewModel(text.hashCode().toLong())

