package com.chrynan.launcher.ui.icon

import android.content.Context
import android.support.annotation.Dimension
import com.chrynan.launcher.R
import com.chrynan.launcher.model.LauncherItem

class ShortcutBadgeIconResolver(context: Context) : AppIconResolver(context),
        BadgeSizeIconResolver<LauncherItem.SingleItem.App?> {

    @Dimension
    override var badgeSizeSmall: Int = context.resources.getDimensionPixelSize(R.dimen.badge_small_size_default)

    @Dimension
    override var badgeSizeLarge: Int = context.resources.getDimensionPixelSize(R.dimen.badge_large_size_default)
}