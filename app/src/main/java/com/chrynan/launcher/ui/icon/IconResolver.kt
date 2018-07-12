package com.chrynan.launcher.ui.icon

import android.graphics.drawable.Drawable
import android.widget.ImageView

interface IconResolver<T> {

    var target: ImageView?

    fun resolve(item: T, onSuccess: ((Drawable?) -> Unit)? = null)
}