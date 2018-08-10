package com.chrynan.launcher.ui.adapter.core

import android.support.v7.widget.RecyclerView
import android.view.View
import com.chrynan.launcher.model.AdapterViewModel
import com.chrynan.launcher.util.ViewResourceId
import com.chrynan.launcher.util.ViewType

interface DelegateAdapter {

    val viewType: ViewType

    val viewResourceId: ViewResourceId

    fun handlesModel(item: AdapterViewModel): Boolean

    fun bindModel(item: AdapterViewModel, view: View)

    class DelegateViewHolder(view: View) : RecyclerView.ViewHolder(view)
}