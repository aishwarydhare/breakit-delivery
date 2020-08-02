package com.breakit.delivery.di

import android.app.Application
import com.breakit.delivery.BreakItDriverApplication
import com.breakit.delivery.di.bindings.ServiceBindings
import com.breakit.delivery.di.modules.AppModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AppModule::class,
        AppBindings::class,
        ServiceBindings::class
    ]
)
interface AppComponent : AndroidInjector<BreakItDriverApplication> {
    fun inject(app: Application)

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun app(application: Application): Builder

        fun build(): AppComponent
    }
}
