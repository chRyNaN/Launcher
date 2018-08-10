package com.chrynan.launcher.ui.grid

import android.view.View
import com.chrynan.launcher.util.ColumnCount
import com.chrynan.launcher.util.RowCount

const val DEFAULT_COLUMN_COUNT = 1
const val DEFAULT_ROW_COUNT = 1
const val DEFAULT_COLUMN_INDEX = 0
const val DEFAULT_ROW_INDEX = 0

val EMPTY_GRID_POSITION = GridPosition(DEFAULT_COLUMN_INDEX, DEFAULT_ROW_INDEX)

val View.columnCount: ColumnCount
    get() = (this as? Spannable)?.columnCount ?: DEFAULT_COLUMN_COUNT

val View.rowCount: RowCount
    get() = (this as? Spannable)?.rowCount ?: DEFAULT_ROW_COUNT

val View.gridPosition: GridPosition
    get() = (this as? Spannable)?.gridPosition ?: EMPTY_GRID_POSITION