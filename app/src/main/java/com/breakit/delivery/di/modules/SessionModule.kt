package com.breakit.delivery.di.modules

import android.app.Application
import com.breakit.delivery.data.login.LoginPrefs
import com.breakit.delivery.network.RequestHelper
import dagger.Module
import dagger.Provides

@Module
abstract class SessionModule {

    @Module
    companion object {

        @JvmStatic
        @Provides
        fun provideRequestHelper(
            application: Application,
            loginPrefs: LoginPrefs
        ): RequestHelper =
            RequestHelper(
                apiAuthToken = loginPrefs.authToken()
            )
    }
}
