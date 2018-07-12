package com.chrynan.launcher.model

enum class FeedWebSource(val typeId: Int) {

    WEB_VIEW(0),
    CHROME_CUSTOM_TABS(1);

    companion object {

        const val TYPE_ID_WEB_VIEW = 0
        const val TYPE_ID_CHROME_CUSTOM_TABS = 1
    }
}