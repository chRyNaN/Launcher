package com.chrynan.launcher.mapper.database

import com.chrynan.launcher.database.entities.FeedSettingsEntity
import com.chrynan.launcher.model.FeedSettings
import javax.inject.Inject

class FeedSettingsEntityMapper @Inject constructor(
        private val feedTypeEntityMapper: FeedTypeEntityMapper,
        private val feedRestrictionEntityMapper: FeedRestrictionEntityMapper
) : DatabaseMapper<FeedSettings, FeedSettingsEntity> {

    override fun mapToDatabaseEntity(model: FeedSettings) = model.run {
        FeedSettingsEntity(webSource = webSource).apply {
            typeEntity.target = feedTypeEntityMapper.mapToDatabaseEntity(type)
            restrictionEntities.addAll(restrictions.map(feedRestrictionEntityMapper::mapToDatabaseEntity))
        }
    }

    override fun mapToModel(entity: FeedSettingsEntity) = entity.run {
        FeedSettings(
                webSource = webSource,
                type = feedTypeEntityMapper.mapToModel(typeEntity.target),
                restrictions = restrictionEntities.map(feedRestrictionEntityMapper::mapToModel)
        )
    }
}