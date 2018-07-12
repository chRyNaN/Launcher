package com.chrynan.launcher.mapper.database

import com.chrynan.launcher.database.entities.FeedRestrictionEntity
import com.chrynan.launcher.model.FeedRestriction
import com.chrynan.launcher.util.emptyString
import javax.inject.Inject

class FeedRestrictionEntityMapper @Inject constructor() : DatabaseMapper<FeedRestriction, FeedRestrictionEntity> {

    override fun mapToDatabaseEntity(model: FeedRestriction) = model.run {
        when (this) {
            is FeedRestriction.Intent -> FeedRestrictionEntity.newIntent(intentType)
            is FeedRestriction.Package -> FeedRestrictionEntity.newPackage(packageName)
        }
    }

    override fun mapToModel(entity: FeedRestrictionEntity) = entity.run {
        when (type) {
            FeedRestrictionEntity.TYPE_INTENT -> FeedRestriction.Intent(intentType ?: emptyString())
            else -> FeedRestriction.Package(packageName ?: emptyString())
        }
    }
}