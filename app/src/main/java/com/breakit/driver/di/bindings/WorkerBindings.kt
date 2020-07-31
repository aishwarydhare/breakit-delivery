package com.breakit.driver.di.bindings

import com.breakit.driver.ui.settings.SignOutWorker
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class WorkerBindings {

    @ContributesAndroidInjector
    abstract fun bindSignOutWorker(): SignOutWorker

}
