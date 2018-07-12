package com.chrynan.launcher.ui.grid

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import com.chrynan.launcher.R
import com.chrynan.launcher.util.measureSpec

open class DynamicGridView : ViewGroup {

    val maxVisibleColumns: ColumnCount
        get() = measuredWidth / spanColumnSize

    val maxVisibleRows: RowCount
        get() = measuredHeight / spanRowSize

    var spanColumnSize: SpanColumnSize = context.resources.getDimensionPixelSize(R.dimen.dynamic_grid_view_default_span_column_size)
        set(value) {
            field = value.coerceAtLeast(0)
        }
    var spanRowSize: SpanRowSize = context.resources.getDimensionPixelSize(R.dimen.dynamic_grid_view_default_span_row_size)
        set(value) {
            field = value.coerceAtLeast(0)
        }

    constructor(context: Context) : this(context, null)

    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : this(context, attrs, defStyleAttr, 0)

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int, defStyleRes: Int) : super(context, attrs, defStyleAttr, defStyleRes) {
        context.obtainStyledAttributes(attrs, R.styleable.DynamicGridView, defStyleAttr, defStyleRes).apply {
            spanColumnSize = getDimensionPixelSize(R.styleable.DynamicGridView_spanColumnSize, spanColumnSize)
            spanRowSize = getDimensionPixelSize(R.styleable.DynamicGridView_spanRowSize, spanRowSize)

            recycle()
        }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)

        (0 until childCount).forEach { index ->
            val childView = getChildAt(index)

            val childWidth = childView.columnCount * spanColumnSize
            val childHeight = childView.rowCount * spanRowSize

            childView.measure(measureSpec(childWidth, MeasureSpec.EXACTLY), measureSpec(childHeight, MeasureSpec.EXACTLY))
        }
    }

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        if (changed) {
            val startX = l + paddingLeft
            val startY = t + paddingTop
            val endX = r - paddingRight
            val endY = b - paddingBottom

            (0 until childCount).forEach { index ->
                val childView = getChildAt(index)

                val childX = (startX + (childView.gridPosition.columnIndex * spanColumnSize)).coerceAtMost(endX)
                val childY = (startY + (childView.gridPosition.rowIndex * spanRowSize)).coerceAtMost(endY)
                val childMaxWidth = (endX - childX).coerceAtLeast(0)
                val childMaxHeight = (endY - childY).coerceAtLeast(0)
                val childEndX = (childX + childView.measuredWidth).coerceAtMost(childX + childMaxWidth)
                val childEndY = (childY + childView.measuredHeight).coerceAtMost(childY + childMaxHeight)

                childView.layout(childX, childY, childEndX, childEndY)
            }
        }
    }

    fun canItemFitInGrid(columnCount: ColumnCount, rowCount: RowCount, gridPosition: GridPosition): Boolean {
        if ((gridPosition.columnIndex < 0) or (gridPosition.rowIndex < 0)) return false
        if (gridPosition.columnIndex + columnCount > maxVisibleColumns) return false
        if (gridPosition.rowIndex + rowCount > maxVisibleRows) return false

        return true
    }

    fun canViewFitInGrid(view: View) = canItemFitInGrid(view.columnCount, view.rowCount, view.gridPosition)
}