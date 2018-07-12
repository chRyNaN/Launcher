package com.chrynan.launcher.ui.icon

import android.content.Context
import android.content.pm.LauncherApps
import android.graphics.drawable.Drawable
import android.os.Build
import android.support.annotation.ColorInt
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.chrynan.launcher.R
import com.chrynan.launcher.model.LauncherItem
import com.chrynan.launcher.util.PackageDrawableFunction
import com.chrynan.launcher.util.ShortcutDrawableFunction
import com.chrynan.launcher.util.loadDrawablesTogether
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class FolderIconResolver(private val context: Context) : IconResolver<LauncherItem.Folder?> {

    override var target: ImageView? = null

    @ColorInt
    var backgroundColor = context.getColor(R.color.launcher_item_view_default_folder_background_color)
        set(value) {
            field = value

            folderIconDrawable.backgroundColor = value
        }

    private val packageManager by lazy { context.packageManager }
    private val launcherApps by lazy { context.getSystemService(LauncherApps::class.java) }
    private val folderIconDrawable by lazy { FolderIconDrawable(context) }

    private var disposable: Disposable? = null

    override fun resolve(item: LauncherItem.Folder?, onSuccess: ((Drawable?) -> Unit)?) {
        item?.run {
            val requests = items.map {
                when {
                    it is LauncherItem.SingleItem.App ->
                        Glide.with(context)
                                .load(PackageDrawableFunction(packageManager, it.packageName))
                    it is LauncherItem.SingleItem.Shortcut && Build.VERSION.SDK_INT >= Build.VERSION_CODES.N_MR1 ->
                        Glide.with(context)
                                .load(ShortcutDrawableFunction(context, launcherApps, it.shortcutInfo))
                    else -> null
                }
            }.filterNotNull()

            disposable?.dispose()

            if (requests.isNotEmpty()) {
                disposable = loadDrawablesTogether(requests)
                        .observeOn(Schedulers.io())
                        .map {
                            folderIconDrawable.apply {
                                drawables = it
                            }
                        }
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe({
                            target?.setImageDrawable(it)
                            onSuccess?.invoke(it)
                        }, {
                            target?.setImageDrawable(null)
                            onSuccess?.invoke(null)
                        })
            } else {
                target?.setImageDrawable(null)
                onSuccess?.invoke(null)
            }
        }
    }
}