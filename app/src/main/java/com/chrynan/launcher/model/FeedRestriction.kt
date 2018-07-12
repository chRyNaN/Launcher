package com.chrynan.launcher.model

import com.chrynan.launcher.util.PackageName

sealed class FeedRestriction {

    data class Package(val packageName: PackageName) : FeedRestriction()

    data class Intent(val intentType: String) : FeedRestriction()
}