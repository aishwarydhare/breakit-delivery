package com.breakit.delivery.di.bindings

import com.breakit.delivery.ui.settings.SignOutWorker
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class WorkerBindings {

    @ContributesAndroidInjector
    abstract fun bindSignOutWorker(): SignOutWorker

}
