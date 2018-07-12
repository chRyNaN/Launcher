package com.chrynan.launcher.mapper.database

import com.chrynan.launcher.database.entities.AppMaskEntity
import com.chrynan.launcher.model.AppMask
import javax.inject.Inject

class AppMaskEntityMapper @Inject constructor() : DatabaseMapper<AppMask, AppMaskEntity> {

    override fun mapToDatabaseEntity(model: AppMask) = model.run {
        AppMaskEntity(
                packageName = packageName,
                maskedName = maskedName,
                maskedIconFileName = maskedIconFileName
        )
    }

    override fun mapToModel(entity: AppMaskEntity) = entity.run {
        AppMask(
                packageName = packageName,
                maskedName = maskedName,
                maskedIconFileName = maskedIconFileName
        )
    }
}