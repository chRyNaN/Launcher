package com.chrynan.launcher.ui.adapter

import android.view.View
import com.chrynan.launcher.R
import com.chrynan.launcher.model.AppListSearchViewModel
import com.chrynan.launcher.ui.adapter.core.BaseDelegateAdapter
import com.chrynan.launcher.util.handleClicks
import kotlinx.android.synthetic.main.adapter_app_list_search.view.*
import javax.inject.Inject

class AppListSearchAdapter @Inject constructor(private val listener: ClickListener) : BaseDelegateAdapter<AppListSearchViewModel>() {

    override val viewType = AdapterViewTypes.APP_SEARCH

    override val viewResourceId = R.layout.adapter_app_list_search

    override fun handlesModelType(item: AppListSearchViewModel) = true

    override fun View.bindModelType(item: AppListSearchViewModel) {
        searchButton?.text = item.text
        searchButton?.handleClicks()?.subscribe({ listener.onSearchButtonClick() },
                { logError(it, "Error handling click in ${AppListSearchAdapter::class.java.name} for ${AppListSearchViewModel::class.java.name} = $item.") })
    }

    interface ClickListener {

        fun onSearchButtonClick()
    }
}