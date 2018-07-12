package com.chrynan.launcher.repository

import android.content.pm.ShortcutInfo
import android.os.Build
import android.support.annotation.RequiresApi
import com.chrynan.launcher.model.ShortcutType
import io.reactivex.Single

@RequiresApi(value = Build.VERSION_CODES.N_MR1)
interface ShortcutInfoRepository {

    fun getAll(): Single<List<ShortcutInfo>>

    fun getAllByPackageName(packageName: String): Single<List<ShortcutInfo>>

    fun getAllForType(shortcutType: ShortcutType): Single<List<ShortcutInfo>>

    fun getAllForType(packageName: String, shortcutType: ShortcutType): Single<List<ShortcutInfo>>
}