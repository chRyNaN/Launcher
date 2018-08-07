package com.chrynan.launcher.usecase

import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import com.chrynan.launcher.mapper.AppMapper
import com.chrynan.launcher.model.AppMask
import com.chrynan.launcher.model.LauncherItem
import com.chrynan.launcher.repository.AppMaskRepository
import com.chrynan.launcher.repository.ApplicationInfoRepository
import com.chrynan.launcher.util.sortEachAlphabeticallyBy
import io.reactivex.Observable
import io.reactivex.Single
import javax.inject.Inject

class GetAllApplicationsUseCase @Inject constructor(
        private val context: Context,
        private val packageManager: PackageManager,
        private val applicationInfoRepository: ApplicationInfoRepository,
        private val appMaskRepository: AppMaskRepository,
        private val getBadgeForPackageNameUseCase: GetBadgeForPackageNameUseCase,
        private val mapper: AppMapper
) : NoParamUseCase<Single<List<LauncherItem.SingleItem.App>>>() {

    override fun execute(param: Nothing?): Single<List<LauncherItem.SingleItem.App>> =
            applicationInfoRepository.getAll()
                    .flatMap {
                        Observable.fromIterable(it)
                                .filter { packageManager.getLaunchIntentForPackage(it.packageName) != null }
                                .filter { it.packageName != context.packageName }
                                .map(mapper::map)
                                .flatMapSingle { app ->
                                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N_MR1) {
                                        getBadgeForPackageNameUseCase.execute(app.packageName)
                                                .map { app.copy(appBadge = it) }
                                                .doOnError { logError(it, "Error retrieving badges for ${LauncherItem.SingleItem.App::class.java.name}.") }
                                                .onErrorReturnItem(app)
                                    } else Single.just(app)
                                }
                                .toList()
                    }
                    .flatMap { apps ->
                        appMaskRepository.getAllAppMasks()
                                .map { it.associateBy { it.packageName } }
                                .flatMap { appMaskMap ->
                                    Observable.fromIterable(apps)
                                            .map { it.copy(appMask = appMaskMap[it.packageName]) }
                                            .toList()
                                }
                                .doOnError { logError(it, "Error retrieving ${AppMask::class.java.name}s.") }
                                .onErrorReturnItem(apps)
                    }
                    .sortEachAlphabeticallyBy { it.name }
                    .doOnError { logError(it, "Error retrieving all ${LauncherItem.SingleItem.App::class.java.name}s in ${GetAllApplicationsUseCase::class.java.name}.") }
}