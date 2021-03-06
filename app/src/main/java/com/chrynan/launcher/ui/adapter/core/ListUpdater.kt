package com.chrynan.launcher.ui.adapter.core

import android.support.v7.util.DiffUtil
import com.chrynan.launcher.model.AdapterViewModel

interface ListUpdater {

    val items: List<AdapterViewModel>

    fun updateItems(diffResult: DiffUtil.DiffResult, newItems: List<AdapterViewModel>)
}