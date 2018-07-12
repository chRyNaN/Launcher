package com.chrynan.launcher.di.module

import android.content.Context
import com.chrynan.launcher.database.entities.AppMaskEntity
import com.chrynan.launcher.database.entities.FeedSettingsEntity
import com.chrynan.launcher.database.entities.MyObjectBox
import dagger.Module
import dagger.Provides
import io.objectbox.Box
import io.objectbox.BoxStore
import javax.inject.Singleton

@Module
internal abstract class DatabaseModule {

    @Module
    companion object {

        @Provides
        @JvmStatic
        @Singleton
        fun provideBoxStore(context: Context): BoxStore = MyObjectBox.builder().androidContext(context).build()

        @Provides
        @JvmStatic
        fun provideAppMaskBox(boxStore: BoxStore): Box<AppMaskEntity> = boxStore.boxFor(AppMaskEntity::class.java)

        @Provides
        @JvmStatic
        fun provideFeedSettingsBox(boxStore: BoxStore): Box<FeedSettingsEntity> = boxStore.boxFor(FeedSettingsEntity::class.java)
    }
}