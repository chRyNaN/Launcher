package com.chrynan.launcher.ui.icon

import android.content.Context
import android.content.pm.LauncherApps
import android.graphics.drawable.Drawable
import android.os.Build
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.chrynan.launcher.model.LauncherItem
import com.chrynan.launcher.util.DrawableListener
import com.chrynan.launcher.util.ShortcutDrawableFunction
import com.chrynan.launcher.util.listener

class ShortcutIconResolver(private val context: Context) : IconResolver<LauncherItem.SingleItem.Shortcut> {

    override var target: ImageView? = null

    private val launcherApps by lazy { context.getSystemService(LauncherApps::class.java) }

    override fun resolve(item: LauncherItem.SingleItem.Shortcut, onSuccess: ((Drawable?) -> Unit)?) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N_MR1) {
            target?.let {
                Glide.with(context)
                        .load(ShortcutDrawableFunction(context, launcherApps, item.shortcutInfo))
                        .listener(object : DrawableListener {
                            override fun onError() {}

                            override fun onSuccess(drawable: Drawable) {
                                onSuccess?.invoke(drawable)
                            }
                        })
                        .into(it)
            }
        } else {
            target?.setImageDrawable(null)
            onSuccess?.invoke(null)
        }
    }
}