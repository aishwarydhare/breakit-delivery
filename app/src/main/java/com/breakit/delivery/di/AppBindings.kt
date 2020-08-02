package com.breakit.delivery.di

import com.breakit.delivery.MainActivity
import com.breakit.delivery.di.bindings.FragmentBindings
import com.breakit.delivery.di.bindings.ServiceBindings
import com.breakit.delivery.di.bindings.ViewModelBindings
import com.breakit.delivery.network.AuthInterceptor
import com.breakit.delivery.network.AuthInterceptorImpl
import com.breakit.delivery.utils.Logger
import com.breakit.delivery.utils.LoggerImpl
import com.breakit.delivery.utils.async.ThreadManager
import com.breakit.delivery.utils.async.ThreadManagerImpl
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class AppBindings {

    @ContributesAndroidInjector(
        modules = [
            FragmentBindings::class,
            ViewModelBindings::class
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

