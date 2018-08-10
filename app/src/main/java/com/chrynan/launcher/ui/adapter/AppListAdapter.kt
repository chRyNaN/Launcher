package com.chrynan.launcher.ui.adapter

import android.view.View
import com.chrynan.launcher.R
import com.chrynan.launcher.model.AppListViewModel
import com.chrynan.launcher.model.LauncherItem
import com.chrynan.launcher.ui.adapter.core.BaseDelegateAdapter
import com.chrynan.launcher.util.handleClicks
import kotlinx.android.synthetic.main.adapter_app_list.view.*
import javax.inject.Inject

class AppListAdapter @Inject constructor(private val listener: ClickListener) : BaseDelegateAdapter<AppListViewModel>() {

    override val viewType = AdapterViewTypes.APP_LIST

    override val viewResourceId = R.layout.adapter_app_list

    override fun handlesModelType(item: AppListViewModel) = true

    override fun View.bindModelType(item: AppListViewModel) {
        launcherItemView?.launcherItem = item.app
        launcherItemView?.handleClicks()?.subscribe({ listener.onAppItemClick(item.app) },
                { logError(it, "Error handling click in ${AppListAdapter::class.java.name} for ${AppListViewModel::class.java.name} = $item.") })
    }

    interface ClickListener {

        fun onAppItemClick(app: LauncherItem.SingleItem.App)
    }
}