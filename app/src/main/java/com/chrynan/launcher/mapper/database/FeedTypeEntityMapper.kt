package com.chrynan.launcher.mapper.database

import com.chrynan.launcher.database.entities.FeedTypeEntity
import com.chrynan.launcher.model.FeedType
import com.chrynan.launcher.util.emptyString
import javax.inject.Inject

class FeedTypeEntityMapper @Inject constructor() : DatabaseMapper<FeedType, FeedTypeEntity> {

    override fun mapToDatabaseEntity(model: FeedType) = model.run {
        when (this) {
            FeedType.None -> FeedTypeEntity.newTypeNone()
            is FeedType.WebSite -> FeedTypeEntity.newTypeWeb(url)
            is FeedType.AppFeed -> FeedTypeEntity.newTypeApp()
        }
    }

    override fun mapToModel(entity: FeedTypeEntity) = entity.run {
        when (type) {
            FeedTypeEntity.TYPE_APP -> FeedType.AppFeed
            FeedTypeEntity.TYPE_WEB -> FeedType.WebSite(url ?: emptyString())
            else -> FeedType.None
        }
    }
}