package com.chrynan.launcher.mapper.database

import com.chrynan.launcher.database.entities.DatabaseEntity

interface DatabaseMapper<T, D : DatabaseEntity> {

    fun mapToDatabaseEntity(model: T): D

    fun mapToModel(entity: D): T
}