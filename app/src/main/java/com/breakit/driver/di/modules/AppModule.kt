package com.breakit.driver.di.modules

import android.app.Application
import android.content.Context
import com.breakit.driver.di.bindings.WorkerBindings
import com.breakit.driver.di.modules.viewmodel.ViewModelModule
import dagger.Module
import dagger.Provides
import dagger.android.AndroidInjectionModule
import javax.inject.Singleton

@Module(
    includes = [
        AndroidInjectionModule::class,
        ViewModelModule::class,
        NetModule::class,
        SessionModule::class,
        WorkerBindings::class,
        GpsModule::class
    ]
)
class AppModule {

    @Singleton
    @Provides
    fun provideApplicationContext(application: Application): Context = application

}
