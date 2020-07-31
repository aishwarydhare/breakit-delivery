package com.breakit.driver

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.core.app.ActivityCompat
import androidx.navigation.NavDestination
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupWithNavController
import com.breakit.driver.data.login.LoginRepository
import com.breakit.driver.databinding.ActivityMainBinding
import com.breakit.driver.di.modules.viewmodel.ViewModelFactory
import com.breakit.driver.notifications.NotificationChannels
import com.breakit.driver.ui.login.LoginBottomSheetFragment
import com.breakit.driver.ui.meal.MealDetailsBottomSheetFragment
import com.breakit.driver.utils.GpsUtils
import com.breakit.driver.utils.PermissionsManager
import com.breakit.driver.utils.extensions.*
import dagger.android.support.DaggerAppCompatActivity
import javax.inject.Inject

private const val TAG = "MAIN"

class MainActivity : DaggerAppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    /**
     * Custom factory for viewmodel
     *
     * Custom factory provides app related dependencies
     */
    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    @Inject
    lateinit var loginRepository: LoginRepository

    private val mainViewModel: MainViewModel by viewModels { viewModelFactory }

    var fragment: LoginBottomSheetFragment? = null

    /**
     * onCreate()
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Common.isLoggedIn.value = loginRepository.isLoggedIn()

        binding = setDataBindingView(R.layout.activity_main)

        val navController = findNavController(R.id.nav_host_fragment)

        binding.navView.setupWithNavController(navController)

        navController.addOnDestinationChangedListener { _, destination, _ ->
            onNavDestinationChanged(destination)
        }

        requestGestureUi()

        createNotificationChannels()

        initView()
    }

    private fun initView() {
        binding.permissionTitle = "Need Location Permissions"
        mainViewModel.arePermissionsGranted.set(PermissionsManager.isAllPermissionsGranted(this))
        binding.locationAllowTv.setOnClickListener {
            requestPermissions()
        }
    }

    private fun onAllPermissionsGranted() {
        GpsUtils.isValidationInProgress = false
        mainViewModel.arePermissionsGranted.set(true)
        initObservers()
    }

    private fun requestPermissions() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(
                this@MainActivity,
                Manifest.permission.ACCESS_FINE_LOCATION
            )
        ) {
            ActivityCompat.requestPermissions(
                this@MainActivity,
                Constants.PERMISSIONS_REQUIRED, 1
            )
        } else {
            ActivityCompat.requestPermissions(
                this@MainActivity,
                Constants.PERMISSIONS_REQUIRED, 1
            )
        }
    }

    private fun createNotificationChannels() {
        if (hasNotificationChannelSupport()) {
            NotificationChannels.newInstance(application).createNotificationChannels()
        }
    }

    private fun initObservers() {
        binding.viewModel = mainViewModel
        Common.isLoggedIn.observeForever {
            it?.let {
                if (!it) {
                    showLoginBottomSheetFragment()
                } else {
                    showHomeFragment()
                }
            }
        }
    }

    fun showMealDetailFragment() {
        val fragment = MealDetailsBottomSheetFragment()
        fragment.show(this.supportFragmentManager, "meal detail")
    }

    private fun showLoginBottomSheetFragment() {
        fragment = LoginBottomSheetFragment()
        fragment!!.show(this.supportFragmentManager, "dialog")
        fragment!!.isCancelable = false
    }

    private fun showHomeFragment() {
        fragment?.let {
            fragment?.dismiss()
        }
        findNavController(R.id.nav_host_fragment).navigate(R.id.navigation_home)
    }

    private fun onNavDestinationChanged(destination: NavDestination) {
        // bottom nav is visible on all screen
        //todo: remove
        binding.navView.visibility = View.GONE
        binding.navDivider.visibility = View.GONE

        when (destination.id) {
            R.id.navigation_splash,
            R.id.navigation_map,
            R.id.navigation_home
            -> {
                // Hide bottom nav on screens which don't need it
                binding.navDivider.visibility = View.GONE
                binding.navView.visibility = View.GONE
            }
        }
    }

    /**
     * Handle up navigation with nav controller
     */
    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment)
        return navController.navigateUp(AppBarConfiguration(navController.graph)) ||
                super.onSupportNavigateUp()
    }

    override fun onRequestPermissionsResult(
        requestCode: Int, permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            1 -> {
                if (grantResults.isNotEmpty() && grantResults[0] ==
                    PackageManager.PERMISSION_GRANTED
                ) {
                    showSnackbar("Permission Granted")
                    onAllPermissionsGranted()
                } else {
                    showErrorSnackbar("Permission Denied")
                }
                return
            }
        }
    }

}
