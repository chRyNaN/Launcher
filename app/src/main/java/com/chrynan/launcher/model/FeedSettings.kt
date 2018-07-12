package com.chrynan.launcher.model

data class FeedSettings(
        val type: FeedType = FeedType.None,
        val webSource: FeedWebSource = FeedWebSource.WEB_VIEW,
        val restrictions: List<FeedRestriction> = emptyList()
)