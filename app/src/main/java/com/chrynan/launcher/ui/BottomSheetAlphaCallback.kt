package com.chrynan.launcher.ui

import android.support.design.widget.BottomSheetBehavior
import android.view.View

class BottomSheetAlphaCallback(
        isInitiallyCollapsed: Boolean = true,
        private val collapsedVisibleViews: List<View?> = emptyList(),
        private val expandedVisibleViews: List<View?> = emptyList()
) : BottomSheetBehavior.BottomSheetCallback() {

    companion object {

        private const val OPAQUE = 1f
        private const val TRANSPARENT = 0f
    }

    init {
        val collapsedAlpha = if (isInitiallyCollapsed) OPAQUE else TRANSPARENT
        val expandedAlpha = if (!isInitiallyCollapsed) OPAQUE else TRANSPARENT

        collapsedVisibleViews.forEach { it?.alpha = collapsedAlpha }
        expandedVisibleViews.forEach { it?.alpha = expandedAlpha }
    }

    override fun onSlide(bottomSheet: View, slideOffset: Float) {
        val collapsedAlpha = OPAQUE - slideOffset

        collapsedVisibleViews.forEach { it?.alpha = collapsedAlpha }
        expandedVisibleViews.forEach { it?.alpha = slideOffset }
    }

    override fun onStateChanged(bottomSheet: View, newState: Int) {
        when (newState) {
            BottomSheetBehavior.STATE_EXPANDED -> {
                collapsedVisibleViews.forEach { it?.visibility = View.INVISIBLE }
                expandedVisibleViews.forEach { it?.visibility = View.VISIBLE }
            }
            BottomSheetBehavior.STATE_COLLAPSED -> {
                collapsedVisibleViews.forEach { it?.visibility = View.VISIBLE }
                expandedVisibleViews.forEach { it?.visibility = View.INVISIBLE }
            }
            BottomSheetBehavior.STATE_DRAGGING -> {
                collapsedVisibleViews.forEach { it?.visibility = View.VISIBLE }
                expandedVisibleViews.forEach { it?.visibility = View.VISIBLE }
            }
        }
    }
}