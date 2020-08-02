package com.breakit.delivery.di.bindings

import com.breakit.delivery.ui.home.HomeFragment
import com.breakit.delivery.ui.login.LoginBottomSheetFragment
import com.breakit.delivery.ui.meal.MealDetailsBottomSheetFragment
import com.breakit.delivery.ui.orders.OrdersFragment
import com.breakit.delivery.ui.splash.SplashFragment
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
