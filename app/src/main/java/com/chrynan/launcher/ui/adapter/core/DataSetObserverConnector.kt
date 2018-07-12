package com.chrynan.launcher.ui.adapter.core

import android.database.DataSetObserver
import android.support.v7.widget.RecyclerView

class DataSetObserverConnector(private val observer: DataSetObserver?) : RecyclerView.AdapterDataObserver() {

    override fun onChanged() = doOnChange()

    override fun onItemRangeChanged(positionStart: Int, itemCount: Int) = doOnChange()

    override fun onItemRangeChanged(positionStart: Int, itemCount: Int, payload: Any?) = doOnChange()

    override fun onItemRangeInserted(positionStart: Int, itemCount: Int) = doOnChange()

    override fun onItemRangeRemoved(positionStart: Int, itemCount: Int) = doOnChange()

    override fun onItemRangeMoved(fromPosition: Int, toPosition: Int, itemCount: Int) = doOnChange()

    private fun doOnChange() {
        observer?.onChanged()
    }
}