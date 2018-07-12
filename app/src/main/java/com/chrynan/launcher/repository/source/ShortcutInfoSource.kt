package com.chrynan.launcher.repository.source

import android.content.pm.LauncherApps
import android.content.pm.LauncherApps.ShortcutQuery.*
import android.content.pm.ShortcutInfo
import android.os.Build
import android.os.Process
import android.support.annotation.RequiresApi
import com.chrynan.launcher.exception.NotDefaultLauncherException
import com.chrynan.launcher.model.ShortcutType
import com.chrynan.launcher.repository.ApplicationInfoRepository
import com.chrynan.launcher.repository.ShortcutInfoRepository
import com.chrynan.launcher.repository.UserHandleRepository
import io.reactivex.Observable
import io.reactivex.Single
import javax.inject.Inject

@RequiresApi(value = Build.VERSION_CODES.N_MR1)
class ShortcutInfoSource @Inject constructor(
        private val applicationInfoRepository: ApplicationInfoRepository,
        private val userHandleRepository: UserHandleRepository,
        private val launcherApps: LauncherApps
) : ShortcutInfoRepository {

    override fun getAll(): Single<List<ShortcutInfo>> =
            applicationInfoRepository.getAll()
                    .flatMap {
                        Observable.fromIterable(it)
                                .flatMapSingle { getAllByPackageName(it.packageName) }
                                .toList()
                    }
                    .map {
                        val list = mutableListOf<ShortcutInfo>()

                        it.forEach { list.addAll(it) }

                        list
                    }

    override fun getAllByPackageName(packageName: String): Single<List<ShortcutInfo>> =
            userHandleRepository.getCurrentUser()
                    .map {
                        try {
                            launcherApps.getShortcuts(LauncherApps.ShortcutQuery()
                                    .setQueryFlags(FLAG_MATCH_DYNAMIC or FLAG_MATCH_MANIFEST or FLAG_MATCH_PINNED)
                                    .setPackage(packageName), Process.myUserHandle())
                        } catch (e: SecurityException) {
                            throw NotDefaultLauncherException("Can only retrieve ${ShortcutInfo::class.java.name} as Default Launcher Application.")
                        }
                    }

    override fun getAllForType(shortcutType: ShortcutType): Single<List<ShortcutInfo>> =
            applicationInfoRepository.getAll()
                    .flatMap {
                        Observable.fromIterable(it)
                                .flatMapSingle { getAllForType(it.packageName, shortcutType) }
                                .toList()
                    }
                    .map {
                        val list = mutableListOf<ShortcutInfo>()

                        it.forEach { list.addAll(it) }

                        list
                    }

    override fun getAllForType(packageName: String, shortcutType: ShortcutType): Single<List<ShortcutInfo>> =
            userHandleRepository.getCurrentUser()
                    .map {
                        try {
                            launcherApps.getShortcuts(LauncherApps.ShortcutQuery()
                                    .setQueryFlags(shortcutType.queryFlag)
                                    .setPackage(packageName), Process.myUserHandle())
                        } catch (e: SecurityException) {
                            throw NotDefaultLauncherException("Can only retrieve ${ShortcutInfo::class.java.name} as Default Launcher Application.")
                        }
                    }
}