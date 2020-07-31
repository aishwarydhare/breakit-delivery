package com.breakit.driver.di.modules

import android.app.Application
import com.breakit.driver.data.login.LoginPrefs
import com.breakit.driver.network.RequestHelper
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
