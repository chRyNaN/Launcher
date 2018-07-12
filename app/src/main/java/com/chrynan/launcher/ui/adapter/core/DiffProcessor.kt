package com.chrynan.launcher.ui.adapter.core

import android.support.v7.util.DiffUtil
import com.chrynan.launcher.model.AdapterViewModel
import javax.inject.Inject

class DiffProcessor @Inject constructor() : DiffUtil.Callback() {

    var oldList: List<AdapterViewModel> = emptyList()
        private set

    var newList: List<AdapterViewModel> = emptyList()
        private set(value) {
            oldList = field
            field = value
        }

    override fun getOldListSize() = oldList.size

    override fun getNewListSize() = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int) =
            oldList[oldItemPosition].uniqueId == newList[newItemPosition].uniqueId

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int) =
            oldList[oldItemPosition] == newList[newItemPosition]

    fun process(newList: List<AdapterViewModel>): DiffUtil.DiffResult {
        this.newList = newList
        return DiffUtil.calculateDiff(this)
    }
}