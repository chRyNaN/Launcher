package com.chrynan.launcher.mapper

import com.chrynan.launcher.model.Badge
import com.chrynan.launcher.model.NotificationWrapper
import javax.inject.Inject

class BadgeMapper @Inject constructor() : Mapper<NotificationWrapper, Badge> {

    override fun map(model: NotificationWrapper) =
            Badge(
                    appPackageName = model.packageName,
                    count = model.notifications.size,
                    size = Badge.Size.LARGE,
                    enabled = true)
}