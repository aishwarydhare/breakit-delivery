package com.breakit.driver.di.bindings

import com.breakit.driver.service.LocationService
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ServiceBindings {

    @ContributesAndroidInjector
    abstract fun contributeLocationService(): LocationService

}
