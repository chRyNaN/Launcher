package com.chrynan.launcher.repository.source

import com.chrynan.launcher.database.entities.FeedSettingsEntity
import com.chrynan.launcher.mapper.database.FeedSettingsEntityMapper
import com.chrynan.launcher.model.FeedSettings
import com.chrynan.launcher.repository.FeedSettingsRepository
import io.objectbox.Box
import io.reactivex.Completable
import io.reactivex.Single
import javax.inject.Inject

class FeedSettingsSource @Inject constructor(
        private val box: Box<FeedSettingsEntity>,
        private val mapper: FeedSettingsEntityMapper
) : FeedSettingsRepository {

    override fun getFeedsSettings(): Single<FeedSettings> =
            Single.fromCallable { box.all.first() }
                    .map(mapper::mapToModel)

    override fun updateFeedSettings(feedSettings: FeedSettings): Completable =
            Single.just(feedSettings)
                    .map(mapper::mapToDatabaseEntity)
                    .map {
                        box.store.runInTx {
                            box.removeAll()
                            box.put(it)
                        }
                    }
                    .ignoreElement()
}