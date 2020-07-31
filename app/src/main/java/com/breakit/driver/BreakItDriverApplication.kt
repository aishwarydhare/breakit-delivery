package com.breakit.driver

import android.app.Application
import com.breakit.driver.data.location.LocationRepository
import com.breakit.driver.di.DaggerAppComponent
import com.breakit.driver.utils.async.ThreadManager
import com.google.android.libraries.places.api.Places
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException
import java.util.*
import javax.inject.Inject

class BreakItDriverApplication : Application(), HasAndroidInjector {

    /**
     * Injector for Android components
     */
    @Inject
    lateinit var androidInjector: DispatchingAndroidInjector<Any>

    /**
     * Delegate class to handle app lifecycle events
     */
    @Inject
    lateinit var appLifeCycleDelegate: AppLifeCycleDelegate

    @Inject
    lateinit var threadManager: ThreadManager

    @Inject
    lateinit var locationRepository: LocationRepository

    /**
     * See [Application.onCreate]
     */
    override fun onCreate() {
        super.onCreate()
        DaggerAppComponent.builder().app(this)
            .build()
            .inject(this)
        if (!Places.isInitialized()) {
            Places.initialize(
                applicationContext,
                getString(R.string.google_maps_key),
                Locale.US
            );
        }
    }

    fun syncLocationWithRemote(lat: String, lon: String) {
        CoroutineScope(Dispatchers.IO).launch {
            val (genericResponse, result) = try {
                locationRepository.updateLocation(lat, lon)
            } catch (exception: IOException) {
                null to false
            } catch (exception: HttpException) {
                null to false
            }
            if (result) genericResponse?.data!!.let {
                Common.slat = lat
                Common.slon = lon
            }
        }
    }

    /**
     * @return injector for Android components
     */
    override fun androidInjector(): AndroidInjector<Any> = androidInjector

}
