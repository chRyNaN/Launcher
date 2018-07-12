package com.chrynan.launcher.ui.adapter.core

import com.chrynan.launcher.model.AdapterViewModel

interface DataBindAdapter {

    val viewType: Int

    val viewResourceId: Int

    val viewModelVariableId: Int

    fun handlesViewItem(item: AdapterViewModel): Boolean

    fun getListener(): Listener? = null

    interface Listener {

        val listenerVariableId: Int
    }
}