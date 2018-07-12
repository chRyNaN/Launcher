package com.chrynan.launcher.model

import com.chrynan.launcher.util.PackageName

data class AppMask(
        val packageName: PackageName,
        val maskedName: String,
        val maskedIconFileName: String
)