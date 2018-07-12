package com.chrynan.launcher.ui.widget

import android.content.Context
import android.support.annotation.ColorInt
import android.support.annotation.Dimension
import android.support.constraint.ConstraintLayout
import android.util.AttributeSet
import android.view.LayoutInflater
import com.chrynan.launcher.R
import com.chrynan.launcher.model.LauncherItem
import com.chrynan.launcher.ui.icon.*
import com.chrynan.launcher.util.setAndReconstruct
import com.chrynan.launcher.util.setTextOrGone
import kotlinx.android.synthetic.main.layout_launcher_item.view.*

class LauncherItemView : ConstraintLayout {

    var launcherItem: LauncherItem? = null
        set(value) {
            field = value

            when (value) {
                is LauncherItem.SingleItem.App -> {
                    appIconResolver.resolve(
                            item = value,
                            onSuccess = {
                                badgeIconResolver.iconDrawable = it
                                badgeIconResolver.resolve(item = value)
                            })
                }
                is LauncherItem.Folder -> {
                    folderIconResolver.resolve(
                            item = value,
                            onSuccess = {
                                badgeIconResolver.iconDrawable = it
                                badgeIconResolver.resolve(item = value)
                            })
                }
                is LauncherItem.SingleItem.Shortcut -> {
                    shortcutIconResolver.resolve(
                            item = value,
                            onSuccess = {
                                badgeIconResolver.iconDrawable = it
                                badgeIconResolver.resolve(item = value)
                            }
                    )
                }
                else -> {
                    iconImageView?.setImageDrawable(null)
                    badgeIconResolver.iconDrawable = null
                    badgeIconResolver.resolve(item = value)
                }
            }

            nameTextView?.setTextOrGone(value?.name)
        }

    private val appIconResolver by lazy { AppIconResolver(context) }
    private val shortcutIconResolver by lazy { ShortcutIconResolver(context) }
    private val shortcutBadgeIconResolver by lazy { ShortcutBadgeIconResolver(context) }
    private val folderIconResolver by lazy { FolderIconResolver(context) }
    private val badgeIconResolver by lazy { BadgeIconResolver(context) }

    @Dimension
    private var badgeSmallSize = context.resources.getDimensionPixelSize(R.dimen.badge_small_size_default)
        set(value) {
            field = value

            badgeIconResolver.badgeSizeSmall = value
            shortcutBadgeIconResolver.badgeSizeSmall = value
        }

    @Dimension
    private var badgeLargeSize = context.resources.getDimensionPixelSize(R.dimen.badge_large_size_default)
        set(value) {
            field = value

            badgeIconResolver.badgeSizeLarge = value
            shortcutBadgeIconResolver.badgeSizeLarge = value
        }

    @ColorInt
    private var defaultBadgeColor = context.getColor(R.color.badge_default_color)
        set(value) {
            field = value

            badgeIconResolver.defaultBadgeColor = value
        }

    @ColorInt
    private var folderBackgroundColor = context.getColor(R.color.launcher_item_view_default_folder_background_color)
        set(value) {
            field = value

            folderIconResolver.backgroundColor = value
        }

    @ColorInt
    private var textColor = context.getColor(R.color.launcher_item_view_default_text_color)
        set(value) {
            field = value

            nameTextView?.setTextColor(value)
        }

    @Dimension
    private var iconSize = context.resources.getDimensionPixelSize(R.dimen.launcher_icon_size)
        set(value) = setAndReconstruct {
            field = value

            iconImageView?.apply {
                layoutParams.width = value
                layoutParams.height = value
            }
        }

    constructor(context: Context) : this(context, null)

    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        LayoutInflater.from(context).inflate(R.layout.layout_launcher_item, this)

        context.obtainStyledAttributes(attrs, R.styleable.LauncherItemView, defStyleAttr, 0).apply {
            defaultBadgeColor = getColor(R.styleable.LauncherItemView_defaultBadgeColor, defaultBadgeColor)
            badgeSmallSize = getDimensionPixelSize(R.styleable.LauncherItemView_badgeSmallSize, badgeSmallSize)
            badgeLargeSize = getDimensionPixelSize(R.styleable.LauncherItemView_badgeLargeSize, badgeLargeSize)
            iconSize = getDimensionPixelSize(R.styleable.LauncherItemView_iconSize, iconSize)
            folderBackgroundColor = getColor(R.styleable.LauncherItemView_folderBackgroundColor, folderBackgroundColor)
            textColor = getColor(R.styleable.LauncherItemView_textColor, textColor)

            recycle()
        }

        minimumWidth = badgeLargeSize + iconSize
        minimumHeight = badgeLargeSize + iconSize

        appIconResolver.target = iconImageView
        shortcutIconResolver.target = iconImageView
        shortcutIconResolver.target = shortcutBadgeImageView
        folderIconResolver.target = iconImageView
        badgeIconResolver.target = badgeImageView
    }
}