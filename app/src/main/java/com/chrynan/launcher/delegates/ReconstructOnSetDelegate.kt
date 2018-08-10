package com.chrynan.launcher.delegates

import android.view.View
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

class ReconstructOnSetDelegate<T>(
        initialValue: T,
        private val beforeReconstructAction: ((T) -> Unit)? = null
) : ReadWriteProperty<View, T> {

    private var value: T = initialValue

    override fun getValue(thisRef: View, property: KProperty<*>): T = value

    override fun setValue(thisRef: View, property: KProperty<*>, value: T) {
        this.value = value
        beforeReconstructAction?.invoke(value)
        thisRef.requestLayout()
        thisRef.invalidate()
    }
}