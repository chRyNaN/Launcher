package com.chrynan.launcher.ui.widget

import android.content.Context
import android.util.AttributeSet
import com.chrynan.launcher.R
import com.chrynan.launcher.ui.grid.DynamicGridView
import com.chrynan.launcher.util.measureSpec
import com.chrynan.launcher.util.setAndReconstruct
import com.chrynan.launcher.util.virtualNavigationBarHeight

class DockView : DynamicGridView {

    var includeBottomNavBar = true
        set(value) = setAndReconstruct { field = value }

    private var dockItemHeight = context.resources.getDimensionPixelSize(R.dimen.dock_item_default_size)

    constructor(context: Context) : this(context, null)

    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : this(context, attrs, defStyleAttr, 0)

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int, defStyleRes: Int) : super(context, attrs, defStyleAttr, defStyleRes) {
        context.obtainStyledAttributes(attrs, R.styleable.DockView, defStyleAttr, defStyleRes).apply {
            includeBottomNavBar = getBoolean(R.styleable.DockView_includeBottomNavBar, true)
            dockItemHeight = getDimensionPixelSize(R.styleable.DockView_dockItemHeight, dockItemHeight)
            recycle()
        }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val expectedHeight = if (includeBottomNavBar) {
            dockItemHeight + paddingTop + paddingBottom + context.virtualNavigationBarHeight
        } else {
            dockItemHeight + paddingTop + paddingBottom
        }

        super.onMeasure(widthMeasureSpec, measureSpec(expectedHeight, MeasureSpec.EXACTLY))
    }
}