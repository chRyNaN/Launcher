package com.chrynan.launcher.resources

import android.app.Application
import com.chrynan.launcher.util.ResourceId

object ResourceProvider : ResourceFetcher {

    lateinit var application: Application

    override fun string(resourceId: ResourceId): Lazy<String> = lazy { application.getString(resourceId) }
}