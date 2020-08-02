package com.breakit.delivery.di.bindings

import com.breakit.delivery.service.LocationService
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ServiceBindings {

    @ContributesAndroidInjector
    abstract fun contributeLocationService(): LocationService

}
