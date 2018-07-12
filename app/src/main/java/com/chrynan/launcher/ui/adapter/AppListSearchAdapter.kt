package com.chrynan.launcher.ui.adapter

import com.chrynan.launcher.BR
import com.chrynan.launcher.R
import com.chrynan.launcher.model.AdapterViewModel
import com.chrynan.launcher.model.AppListSearchViewModel
import com.chrynan.launcher.ui.adapter.core.AdapterViewTypes
import com.chrynan.launcher.ui.adapter.core.DataBindAdapter
import javax.inject.Inject

class AppListSearchAdapter @Inject constructor(private val listener: ClickListener) : DataBindAdapter {

    override val viewType = AdapterViewTypes.APP_SEARCH

    override val viewResourceId = R.layout.adapter_app_list_search

    override val viewModelVariableId = BR.model

    override fun handlesViewItem(item: AdapterViewModel) = item is AppListSearchViewModel

    override fun getListener() = listener

    interface ClickListener : DataBindAdapter.Listener {

        override val listenerVariableId: Int
            get() = 0

        fun onSearchButtonClick()
    }
}