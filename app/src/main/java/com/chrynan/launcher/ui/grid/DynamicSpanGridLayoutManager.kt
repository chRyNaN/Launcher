package com.chrynan.launcher.ui.grid

import android.content.Context
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import com.chrynan.launcher.util.ViewType

typealias SpanCount = Int

class DynamicSpanGridLayoutManager(context: Context, viewTypeSpanMap: Map<ViewType, SpanCount> = emptyMap(), spanCount: SpanCount = DEFAULT_MAX_SPAN_COUNT) : GridLayoutManager(context, spanCount) {

    companion object {

        const val DEFAULT_ITEM_SPAN_COUNT = 1
        const val DEFAULT_MAX_SPAN_COUNT = 4
    }

    private var adapter: RecyclerView.Adapter<*>? = null

    init {
        spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {

            override fun getSpanSize(position: Int) = adapter?.getItemViewType(position)?.let { viewTypeSpanMap[it] }
                    ?: DEFAULT_ITEM_SPAN_COUNT
        }
    }

    override fun onAdapterChanged(oldAdapter: RecyclerView.Adapter<*>?, newAdapter: RecyclerView.Adapter<*>?) {
        super.onAdapterChanged(oldAdapter, newAdapter)

        adapter = newAdapter
    }
}