package com.chrynan.launcher.ui.icon

import android.content.Context
import android.graphics.drawable.Drawable
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.chrynan.launcher.model.LauncherItem
import com.chrynan.launcher.util.DrawableListener
import com.chrynan.launcher.util.PackageDrawableFunction
import com.chrynan.launcher.util.listener

open class AppIconResolver(private val context: Context) : IconResolver<LauncherItem.SingleItem.App?> {

    override var target: ImageView? = null

    private val packageManager by lazy { context.packageManager }

    override fun resolve(item: LauncherItem.SingleItem.App?, onSuccess: ((Drawable?) -> Unit)?) {
        target?.run {
            if (item == null) {
                setImageDrawable(null)
                onSuccess?.invoke(null)
            } else {
                Glide.with(context)
                        .load(PackageDrawableFunction(packageManager, item.packageName))
                        .listener(object : DrawableListener {
                            override fun onError() {}

                            override fun onSuccess(drawable: Drawable) {
                                onSuccess?.invoke(drawable)
                            }
                        })
                        .into(this)
            }
        }
    }
}