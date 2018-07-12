package com.chrynan.launcher.repository

import android.os.UserHandle
import io.reactivex.Single

interface UserHandleRepository {

    fun getAll(): Single<List<UserHandle>>

    fun getCurrentUser(): Single<UserHandle>
}