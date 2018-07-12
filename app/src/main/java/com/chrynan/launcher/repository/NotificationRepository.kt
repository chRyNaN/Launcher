package com.chrynan.launcher.repository

import android.service.notification.StatusBarNotification
import io.reactivex.Observable
import io.reactivex.Single

interface NotificationRepository {

    fun getAllNotifications(): Observable<List<StatusBarNotification>>

    fun getAllNotificationsForCurrentApp(): Single<List<StatusBarNotification>>
}