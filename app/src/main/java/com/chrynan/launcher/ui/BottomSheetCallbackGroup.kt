package com.chrynan.launcher.ui

import android.support.design.widget.BottomSheetBehavior
import android.view.View

class BottomSheetCallbackGroup(private val callbacks: Set<BottomSheetBehavior.BottomSheetCallback>) : BottomSheetBehavior.BottomSheetCallback() {

    override fun onSlide(bottomSheet: View, slideOffset: Float) =
            callbacks.forEach { it.onSlide(bottomSheet, slideOffset) }

    override fun onStateChanged(bottomSheet: View, newState: Int) =
            callbacks.forEach { it.onStateChanged(bottomSheet, newState) }
}