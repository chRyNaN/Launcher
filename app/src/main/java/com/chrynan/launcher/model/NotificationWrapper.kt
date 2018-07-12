package com.chrynan.launcher.model

import android.content.pm.ShortcutInfo
import com.chrynan.launcher.util.PackageName

data class NotificationWrapper(
        val packageName: PackageName,
        val notifications: List<ShortcutInfo>
)