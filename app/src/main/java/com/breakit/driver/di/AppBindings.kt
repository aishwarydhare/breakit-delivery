package com.breakit.driver.di

import com.breakit.driver.MainActivity
import com.breakit.driver.di.bindings.FragmentBindings
import com.breakit.driver.di.bindings.ServiceBindings
import com.breakit.driver.di.bindings.ViewModelBindings
import com.breakit.driver.network.AuthInterceptor
import com.breakit.driver.network.AuthInterceptorImpl
import com.breakit.driver.utils.Logger
import com.breakit.driver.utils.LoggerImpl
import com.breakit.driver.utils.async.ThreadManager
import com.breakit.driver.utils.async.ThreadManagerImpl
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class AppBindings {

    @ContributesAndroidInjector(
        modules = [
            FragmentBindings::class,
            ViewModelBindings::class,
            ServiceBindings::class
        ]
    )
    abstract fun contributeMainActivity(): MainActivity

    @Binds
    abstract fun provideThreadManager(threadManager: ThreadManagerImpl): ThreadManager

    @Binds
    abstract fun provideAuthInterceptor(authInterceptor: AuthInterceptorImpl): AuthInterceptor

    @Binds
    abstract fun provideLogger(logger: LoggerImpl): Logger
}

