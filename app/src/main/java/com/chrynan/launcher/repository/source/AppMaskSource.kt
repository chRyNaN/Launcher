package com.chrynan.launcher.repository.source

import com.chrynan.launcher.database.entities.AppMaskEntity
import com.chrynan.launcher.mapper.database.AppMaskEntityMapper
import com.chrynan.launcher.model.AppMask
import com.chrynan.launcher.repository.AppMaskRepository
import com.chrynan.launcher.util.mapEachFromEmittedList
import io.objectbox.Box
import io.reactivex.Completable
import io.reactivex.Single
import javax.inject.Inject

class AppMaskSource @Inject constructor(
        private val box: Box<AppMaskEntity>,
        private val mapper: AppMaskEntityMapper
) : AppMaskRepository {

    override fun getAllAppMasks(): Single<List<AppMask>> =
            Single.fromCallable { box.all }
                    .mapEachFromEmittedList(mapper::mapToModel)

    override fun updateAllAppMasks(appMasks: List<AppMask>): Completable =
            Single.just(appMasks)
                    .mapEachFromEmittedList(mapper::mapToDatabaseEntity)
                    .map {
                        box.store.runInTx {
                            box.removeAll()
                            box.put(it)
                        }
                    }
                    .ignoreElement()
}