package com.chrynan.launcher.resources

import android.app.Application
import com.chrynan.launcher.util.ResourceId

object ResourceProvider : ResourceFetcher {

    lateinit var application: Application

    override fun string(resourceId: ResourceId): Lazy<String> = lazy { application.getString(resourceId) }

    override fun dimensionPixelOffset(resourceId: ResourceId): Lazy<Int> = lazy { application.resources.getDimensionPixelOffset(resourceId) }

    override fun dimensionPixelSize(resourceId: ResourceId): Lazy<Int> = lazy { application.resources.getDimensionPixelSize(resourceId) }

    override fun color(resourceId: ResourceId): Lazy<Int> = lazy { application.resources.getColor(resourceId, null) }
}