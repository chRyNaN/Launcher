package com.chrynan.launcher.di.module

import com.chrynan.launcher.repository.*
import com.chrynan.launcher.repository.source.*
import dagger.Binds
import dagger.Module

@Module
internal abstract class RepositoryModule {

    @Binds
    abstract fun bindApplicationInfoRepository(appSource: ApplicationInfoSource): ApplicationInfoRepository

    @Binds
    abstract fun bindShortcutInfoRepository(shortcutInfoSource: ShortcutInfoSource): ShortcutInfoRepository

    @Binds
    abstract fun bindUserHandleRepository(userHandleSource: UserHandleSource): UserHandleRepository

    @Binds
    abstract fun bindNotificationRepository(notificationSource: NotificationSource): NotificationRepository

    @Binds
    abstract fun bindAppMaskRepository(appMaskSource: AppMaskSource): AppMaskRepository

    @Binds
    abstract fun bindFeedSettingsRepository(feedSettingsSource: FeedSettingsSource): FeedSettingsRepository
}