package com.chrynan.launcher.resources

import android.support.annotation.ColorRes
import android.support.annotation.DimenRes
import android.support.annotation.StringRes
import com.chrynan.launcher.util.ResourceId

interface ResourceFetcher {

    fun string(@StringRes resourceId: ResourceId): Lazy<String>

    fun dimensionPixelOffset(@DimenRes resourceId: ResourceId): Lazy<Int>

    fun dimensionPixelSize(@DimenRes resourceId: ResourceId): Lazy<Int>

    fun color(@ColorRes resourceId: ResourceId): Lazy<Int>
}