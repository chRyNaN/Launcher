package com.chrynan.launcher.mapper

import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager
import com.chrynan.launcher.model.LauncherItem
import com.chrynan.launcher.util.emptyString
import javax.inject.Inject

class AppMapper @Inject constructor(private val packageManager: PackageManager) : Mapper<ApplicationInfo, LauncherItem.SingleItem.App> {

    override fun map(model: ApplicationInfo): LauncherItem.SingleItem.App =
            LauncherItem.SingleItem.App(
                    appName = packageManager.getApplicationLabel(model)?.toString()
                            ?: emptyString(),
                    packageName = model.packageName,
                    launchIntent = packageManager.getLaunchIntentForPackage(model.packageName))
}