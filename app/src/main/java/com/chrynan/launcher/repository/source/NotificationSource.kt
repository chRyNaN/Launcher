package com.chrynan.launcher.repository.source

import android.app.NotificationManager
import android.service.notification.NotificationListenerService
import android.service.notification.StatusBarNotification
import com.chrynan.launcher.repository.NotificationRepository
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.subjects.BehaviorSubject
import javax.inject.Inject

class NotificationSource : NotificationListenerService(),
        NotificationRepository {

    @Inject
    lateinit var notificationManager: NotificationManager

    private val notificationRelay = BehaviorSubject.create<List<StatusBarNotification>>()

    private val latestNotifications: MutableList<StatusBarNotification> = mutableListOf()

    override fun onListenerConnected() {
        latestNotifications.clear()
        latestNotifications.addAll(activeNotifications)
        notificationRelay.onNext(latestNotifications)
    }

    override fun onNotificationPosted(notification: StatusBarNotification?) {
        notification?.let(latestNotifications::add)
        notificationRelay.onNext(latestNotifications)
    }

    override fun onNotificationRemoved(notification: StatusBarNotification?) {
        notification?.let(latestNotifications::remove)
        notificationRelay.onNext(latestNotifications)
    }

    override fun getAllNotifications(): Observable<List<StatusBarNotification>> =
            notificationRelay.share()

    override fun getAllNotificationsForCurrentApp(): Single<List<StatusBarNotification>> =
            Single.fromCallable { notificationManager.activeNotifications.toList() }
}