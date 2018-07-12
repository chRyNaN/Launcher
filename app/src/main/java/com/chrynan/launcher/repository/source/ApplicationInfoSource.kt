package com.chrynan.launcher.repository.source

import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager
import com.chrynan.launcher.repository.ApplicationInfoRepository
import io.reactivex.Single
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ApplicationInfoSource @Inject constructor(private val packageManager: PackageManager) : ApplicationInfoRepository {

    override fun getAll(): Single<List<ApplicationInfo>> =
            Single.fromCallable { packageManager.getInstalledApplications(PackageManager.GET_META_DATA) }

    override fun getByPackageName(packageName: String): Single<ApplicationInfo> =
            Single.fromCallable { packageManager.getApplicationInfo(packageName, 0) }
}