package com.chrynan.launcher.ui.adapter.core

import android.view.View
import com.chrynan.launcher.model.AdapterViewModel

interface GenericDelegateAdapter<T : AdapterViewModel> {

    fun handlesModelType(item: T): Boolean

    fun View.bindModelType(item: T)
}