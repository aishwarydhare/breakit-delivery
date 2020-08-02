package com.breakit.delivery

import android.app.Application
import android.util.Log
import com.breakit.delivery.data.location.LocationRepository
import com.breakit.delivery.data.login.LoginRepository
import com.breakit.delivery.di.DaggerAppComponent
import com.breakit.delivery.utils.async.ThreadManager
import com.google.android.libraries.places.api.Places
import com.google.firebase.FirebaseApp
import com.google.firebase.iid.FirebaseInstanceId
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

private const val TAG = "FCM"

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

    @Inject
    lateinit var loginRepository: LoginRepository

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
            )
        }
        FirebaseApp.initializeApp(applicationContext)
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
            if (result) genericResponse?.ok.let {
                Common.slat = lat
                Common.slon = lon
            }
        }
    }

    fun updateFcmTokenOnRemote(token: String) {
        CoroutineScope(Dispatchers.IO).launch {
            val (genericResponse, result) = try {
                loginRepository.fcmUpdateOnRemote(token)
            } catch (exception: IOException) {
                null to false
            } catch (exception: HttpException) {
                null to false
            }
            if (result) genericResponse?.ok?.let {
                Log.d(TAG, "save to remote token $it")
            }
        }
    }

    fun updateFcmTokenOnRemote() {
        FirebaseInstanceId.getInstance().instanceId.addOnSuccessListener { instanceIdResult ->
            val newToken: String = instanceIdResult.getToken()
            Log.e("FCM", newToken)
            if (loginRepository.isLoggedIn()) updateFcmTokenOnRemote(newToken)
        }
    }

    /**
     * @return injector for Android components
     */
    override fun androidInjector(): AndroidInjector<Any> = androidInjector

}
