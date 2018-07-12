package com.chrynan.launcher.di.component

import com.chrynan.launcher.LauncherApplication
import com.chrynan.launcher.di.module.ActivityBuilder
import com.chrynan.launcher.di.module.AppModule
import com.chrynan.launcher.di.module.DatabaseModule
import com.chrynan.launcher.di.module.RepositoryModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(modules = [
    AndroidSupportInjectionModule::class,
    AppModule::class,
    RepositoryModule::class,
    DatabaseModule::class,
    ActivityBuilder::class
])
interface AppComponent : AndroidInjector<LauncherApplication> {

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun application(app: LauncherApplication): AppComponent.Builder

        fun build(): AppComponent
    }
}