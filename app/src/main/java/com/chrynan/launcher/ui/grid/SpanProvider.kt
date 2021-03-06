package com.chrynan.launcher.ui.grid

import com.chrynan.launcher.util.*

interface SpanProvider {

    fun getColumnCount(position: Position, viewType: ViewType, spanColumnSize: SpanColumnSize): ColumnCount

    fun getRowCount(position: Position, viewType: ViewType, spanRowSize: SpanRowSize): RowCount

    fun getGridPosition(position: Position, viewType: ViewType): GridPosition
}