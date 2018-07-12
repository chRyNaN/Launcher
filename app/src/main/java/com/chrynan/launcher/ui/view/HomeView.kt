package com.chrynan.launcher.ui.view

import com.chrynan.launcher.model.LauncherItem

interface HomeView {

    fun showFolder(folder: LauncherItem.Folder)

    fun showShortcut(shortcut: LauncherItem.SingleItem.Shortcut)
}