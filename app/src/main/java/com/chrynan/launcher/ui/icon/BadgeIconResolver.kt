package com.chrynan.launcher.ui.icon

import android.content.Context
import android.graphics.Outline
import android.graphics.PorterDuff
import android.graphics.drawable.Drawable
import android.support.annotation.ColorInt
import android.support.annotation.Dimension
import android.support.v7.graphics.Palette
import android.view.View
import android.view.ViewOutlineProvider
import android.widget.ImageView
import com.chrynan.launcher.R
import com.chrynan.launcher.model.Badge
import com.chrynan.launcher.model.LauncherItem
import com.chrynan.launcher.model.hasBadge
import com.chrynan.launcher.ui.binding.setVisibleOrGone
import com.chrynan.launcher.util.toBitmap
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class BadgeIconResolver(private val context: Context) : IconResolver<LauncherItem?>,
        BadgeSizeIconResolver<LauncherItem?> {

    override var target: ImageView? = null

    @Dimension
    override var badgeSizeSmall: Int = context.resources.getDimensionPixelSize(R.dimen.badge_small_size_default)

    @Dimension
    override var badgeSizeLarge: Int = context.resources.getDimensionPixelSize(R.dimen.badge_large_size_default)

    @ColorInt
    var defaultBadgeColor: Int = context.getColor(R.color.badge_default_color)

    var iconDrawable: Drawable? = null

    private val badgeDrawable by lazy { context.getDrawable(R.drawable.ic_badge) }

    private val badgeOutlineProvider by lazy {
        object : ViewOutlineProvider() {

            override fun getOutline(view: View?, outline: Outline?) {
                outline?.setOval(0, 0, target?.width ?: 0, target?.height ?: 0)
            }
        }
    }

    private var disposable: Disposable? = null

    override fun resolve(item: LauncherItem?, onSuccess: ((Drawable?) -> Unit)?) {
        if (iconDrawable != null) {
            disposable?.dispose()

            disposable = Single.just(iconDrawable)
                    .observeOn(Schedulers.io())
                    .map { it.toBitmap() }
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({
                        Palette.from(it)
                                .generate { palette ->
                                    val color = palette.lightVibrantSwatch?.rgb
                                            ?: defaultBadgeColor

                                    updateBadge(item, color, onSuccess)
                                }
                    }, {})
        } else updateBadge(item, defaultBadgeColor, onSuccess)
    }

    private fun updateBadge(item: LauncherItem?, @ColorInt color: Int, onSuccess: ((Drawable?) -> Unit)?) {
        val size = item?.badge?.size.px

        badgeDrawable.setColorFilter(color, PorterDuff.Mode.SRC_IN)

        badgeDrawable.setBounds(0, 0, size, size)

        target?.apply {
            layoutParams.width = size
            layoutParams.height = size

            setImageDrawable(badgeDrawable)

            setVisibleOrGone(item.hasBadge)

            // Allows Elevation Shadow to be drawn
            outlineProvider = badgeOutlineProvider
        }

        onSuccess?.invoke(badgeDrawable)
    }

    private val Badge.Size?.px
        get() = if (this == Badge.Size.SMALL) badgeSizeSmall else badgeSizeLarge
}