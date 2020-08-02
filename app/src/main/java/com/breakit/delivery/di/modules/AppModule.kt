package com.breakit.delivery.di.modules

import android.app.Application
import android.content.Context
import com.breakit.delivery.di.bindings.WorkerBindings
import com.breakit.delivery.di.modules.viewmodel.ViewModelModule
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
