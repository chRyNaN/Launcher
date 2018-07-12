package com.chrynan.launcher.repository

import android.content.pm.ApplicationInfo
import io.reactivex.Single

interface ApplicationInfoRepository {

    fun getAll(): Single<List<ApplicationInfo>>

    fun getByPackageName(packageName: String): Single<ApplicationInfo>
}