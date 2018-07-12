package com.chrynan.launcher.ui.adapter

import com.chrynan.launcher.BR
import com.chrynan.launcher.R
import com.chrynan.launcher.model.AdapterViewModel
import com.chrynan.launcher.model.AppListViewModel
import com.chrynan.launcher.model.LauncherItem
import com.chrynan.launcher.ui.adapter.core.AdapterViewTypes
import com.chrynan.launcher.ui.adapter.core.DataBindAdapter
import javax.inject.Inject

class AppListAdapter @Inject constructor(private val listener: ClickListener) : DataBindAdapter {

    override val viewType = AdapterViewTypes.APP_LIST

    override val viewResourceId = R.layout.adapter_app_list

    override val viewModelVariableId = BR.model

    override fun handlesViewItem(item: AdapterViewModel) = item is AppListViewModel

    override fun getListener() = listener

    interface ClickListener : DataBindAdapter.Listener {

        override val listenerVariableId
            get() = BR.listener

        fun onAppItemClick(app: LauncherItem.SingleItem.App)
    }
}