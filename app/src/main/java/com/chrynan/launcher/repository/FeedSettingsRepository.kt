package com.chrynan.launcher.repository

import com.chrynan.launcher.model.FeedSettings
import io.reactivex.Completable
import io.reactivex.Single

interface FeedSettingsRepository {

    fun getFeedsSettings(): Single<FeedSettings>

    fun updateFeedSettings(feedSettings: FeedSettings): Completable
}