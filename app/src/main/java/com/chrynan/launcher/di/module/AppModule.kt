package com.chrynan.launcher.di.module

import android.app.NotificationManager
import android.content.Context
import android.content.pm.LauncherApps
import android.content.pm.PackageManager
import android.os.UserManager
import com.chrynan.launcher.LauncherApplication
import dagger.Binds
import dagger.Module
import dagger.Provides

@Module
internal abstract class AppModule {

    @Module
    companion object {

        @Provides
        @JvmStatic
        fun providePackageManager(context: Context): PackageManager = context.packageManager

        @Provides
        @JvmStatic
        fun provideLauncherApps(context: Context): LauncherApps = context.getSystemService(LauncherApps::class.java)

        @Provides
        @JvmStatic
        fun provideUserManager(context: Context): UserManager = context.getSystemService(UserManager::class.java)

        @Provides
        @JvmStatic
        fun provideNotificationManager(context: Context): NotificationManager = context.getSystemService(NotificationManager::class.java)
    }

    @Binds
    abstract fun bindAppContext(application: LauncherApplication): Context
}