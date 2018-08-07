package com.chrynan.launcher.resources

import android.support.annotation.StringRes
import com.chrynan.launcher.util.ResourceId

interface ResourceFetcher {

    fun string(@StringRes resourceId: ResourceId): Lazy<String>
}