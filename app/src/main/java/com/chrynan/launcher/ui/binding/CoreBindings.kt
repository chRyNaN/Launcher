package com.chrynan.launcher.ui.binding

import android.databinding.BindingAdapter
import android.view.View

@BindingAdapter(value = ["setVisibleOrGone"], requireAll = true)
fun View.setVisibleOrGone(isVisible: Boolean) {
    visibility = if (isVisible) View.VISIBLE else View.GONE
}