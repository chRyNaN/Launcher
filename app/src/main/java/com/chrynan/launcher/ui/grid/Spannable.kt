package com.chrynan.launcher.ui.grid

import com.chrynan.launcher.util.ColumnCount
import com.chrynan.launcher.util.RowCount

interface Spannable {

    val columnCount: ColumnCount

    val rowCount: RowCount

    val gridPosition: GridPosition
}