package com.chrynan.launcher.model

data class Badge(
        val appPackageName: String,
        val count: Int,
        val size: Size,
        val enabled: Boolean
) {

    enum class Size {
        SMALL,
        LARGE
    }
}