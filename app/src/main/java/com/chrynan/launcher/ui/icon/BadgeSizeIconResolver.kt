package com.chrynan.launcher.ui.icon

interface BadgeSizeIconResolver<T> : IconResolver<T> {

    var badgeSizeSmall: Int

    var badgeSizeLarge: Int
}