package com.breakit.driver.di.bindings

import com.breakit.driver.ui.home.HomeFragment
import com.breakit.driver.ui.login.LoginBottomSheetFragment
import com.breakit.driver.ui.meal.MealDetailsBottomSheetFragment
import com.breakit.driver.ui.orders.OrdersFragment
import com.breakit.driver.ui.splash.SplashFragment
import com.google.android.gms.maps.MapFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class FragmentBindings {

    @ContributesAndroidInjector
    abstract fun contributeSplashFragment(): SplashFragment

    @ContributesAndroidInjector
    abstract fun contributeLoginDialogFragment(): LoginBottomSheetFragment

    @ContributesAndroidInjector
    abstract fun contributeHomeFragment(): HomeFragment

    @ContributesAndroidInjector
    abstract fun contributeMealDetailsBottomSheetFragment(): MealDetailsBottomSheetFragment

    @ContributesAndroidInjector
    abstract fun contributeOrdersFragment(): OrdersFragment

    @ContributesAndroidInjector
    abstract fun contributeMapFragment(): MapFragment

}
