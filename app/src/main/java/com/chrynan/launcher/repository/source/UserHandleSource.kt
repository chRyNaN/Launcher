package com.chrynan.launcher.repository.source

import android.os.Process
import android.os.UserHandle
import android.os.UserManager
import com.chrynan.launcher.repository.UserHandleRepository
import io.reactivex.Single
import javax.inject.Inject

class UserHandleSource @Inject constructor(private val userManager: UserManager) : UserHandleRepository {

    override fun getAll(): Single<List<UserHandle>> = Single.fromCallable { userManager.userProfiles }

    override fun getCurrentUser(): Single<UserHandle> = Single.fromCallable { Process.myUserHandle() }
}