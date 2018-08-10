package com.chrynan.launcher.ui.adapter.core

import android.view.View
import com.chrynan.launcher.logging.Loggable
import com.chrynan.launcher.logging.Logger
import com.chrynan.launcher.model.AdapterViewModel
import com.chrynan.launcher.resources.ResourceFetcher
import com.chrynan.launcher.resources.ResourceProvider
import com.chrynan.launcher.util.isTruthy

abstract class BaseDelegateAdapter<T : AdapterViewModel> : DelegateAdapter,
        GenericDelegateAdapter<T>,
        Loggable by Logger,
        ResourceFetcher by ResourceProvider {

    @Suppress("UNCHECKED_CAST")
    override fun handlesModel(item: AdapterViewModel): Boolean =
            try {
                (item as? T)?.let { handlesModelType(it) }.isTruthy()
            } catch (e: ClassCastException) {
                false
            }

    @Suppress("UNCHECKED_CAST")
    override fun bindModel(item: AdapterViewModel, view: View) {
        try {
            (item as? T)?.let { view.bindModelType(it) }
        } catch (e: ClassCastException) {
            // Do nothing
        }
    }
}