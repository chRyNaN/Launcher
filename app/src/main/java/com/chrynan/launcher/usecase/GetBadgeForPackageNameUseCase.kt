package com.chrynan.launcher.usecase

import android.os.Build
import android.support.annotation.RequiresApi
import com.chrynan.launcher.mapper.BadgeMapper
import com.chrynan.launcher.model.Badge
import com.chrynan.launcher.model.NotificationWrapper
import com.chrynan.launcher.repository.ShortcutInfoRepository
import com.chrynan.launcher.util.PackageName
import io.reactivex.Single
import javax.inject.Inject

class GetBadgeForPackageNameUseCase @Inject constructor(
        private val shortcutInfoRepository: ShortcutInfoRepository,
        private val mapper: BadgeMapper
) : BaseUseCase<PackageName, Single<Badge>>() {

    @RequiresApi(Build.VERSION_CODES.N_MR1)
    override fun execute(param: PackageName): Single<Badge> =
            shortcutInfoRepository.getAllByPackageName(param)
                    .map { NotificationWrapper(packageName = param, notifications = it) }
                    .map(mapper::map)
                    .doOnError { logError(it, "Error retrieving ${Badge::class.java.name} for $param in ${GetBadgeForPackageNameUseCase::class.java.name}.") }
}