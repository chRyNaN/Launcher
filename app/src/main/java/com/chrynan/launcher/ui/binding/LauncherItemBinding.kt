package com.chrynan.launcher.ui.binding

import android.databinding.BindingAdapter
import com.chrynan.launcher.model.LauncherItem
import com.chrynan.launcher.ui.widget.LauncherItemView

@BindingAdapter(value = ["launcherItem"], requireAll = true)
fun LauncherItemView.setLauncherItem(item: LauncherItem) {
    launcherItem = item
}