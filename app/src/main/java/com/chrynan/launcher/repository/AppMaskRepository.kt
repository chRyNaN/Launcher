package com.chrynan.launcher.repository

import com.chrynan.launcher.model.AppMask
import io.reactivex.Completable
import io.reactivex.Single

interface AppMaskRepository {

    fun getAllAppMasks(): Single<List<AppMask>>

    fun updateAllAppMasks(appMasks: List<AppMask>): Completable
}