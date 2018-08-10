package com.chrynan.launcher.ui.grid

import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import com.chrynan.launcher.util.*

class DynamicGridLayoutManager : RecyclerView.LayoutManager() {

    companion object {

        const val DEFAULT_COLUMN_COUNT = 1
        const val DEFAULT_ROW_COUNT = 1
        const val VERTICAL = RecyclerView.VERTICAL
        const val HORIZONTAL = RecyclerView.HORIZONTAL
    }

    val maxVisibleColumns: ColumnCount
        get() = recyclerViewVisibleWidth / spanColumnSize

    val maxVisibleRows: RowCount
        get() = recyclerViewVisibleHeight / spanRowSize

    var recyclerViewVisibleWidth: Int = 0
        private set

    var recyclerViewVisibleHeight: Int = 0
        private set

    var spanColumnSize: SpanColumnSize = 0
        set(value) {
            field = value.coerceAtLeast(0)
        }
    var spanRowSize: SpanRowSize = 0
        set(value) {
            field = value.coerceAtLeast(0)
        }

    var spanProvider: SpanProvider? = null

    var scrollable = true

    @RecyclerView.Orientation
    var orientation: Orientation = VERTICAL
        set(value) {
            field = value.coerceIn(HORIZONTAL, VERTICAL)
        }

    var paginate = false

    override fun generateDefaultLayoutParams() =
            RecyclerView.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)

    override fun canScrollHorizontally() = scrollable and (orientation == RecyclerView.HORIZONTAL)

    override fun canScrollVertically() = scrollable and (orientation == RecyclerView.VERTICAL)

    override fun onLayoutChildren(recycler: RecyclerView.Recycler?, state: RecyclerView.State?) {

        super.onLayoutChildren(recycler, state)
    }
}