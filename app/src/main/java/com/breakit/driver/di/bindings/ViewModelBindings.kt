package com.breakit.driver.di.bindings

import androidx.lifecycle.ViewModel
import com.breakit.driver.MainViewModel
import com.breakit.driver.di.modules.viewmodel.ViewModelKey
import com.breakit.driver.ui.home.HomeViewModel
import com.breakit.driver.ui.login.LoginBottomSheetViewModel
import com.breakit.driver.ui.meal.MealDetailsBottomSheetViewModel
import com.breakit.driver.ui.orders.OrdersViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelBindings {

    @Binds
    @IntoMap
    @ViewModelKey(MainViewModel::class)
    abstract fun bindMainViewModel(viewModel: MainViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(LoginBottomSheetViewModel::class)
    abstract fun bindLoginViewModel(loginBottomSheetViewModel: LoginBottomSheetViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(HomeViewModel::class)
    abstract fun bindHomeViewModel(homeViewModel: HomeViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(MealDetailsBottomSheetViewModel::class)
    abstract fun bindMealDetailsBottomSheetViewModel(mealDetailsBottomSheetViewModel: MealDetailsBottomSheetViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(OrdersViewModel::class)
    abstract fun bindOrdersViewModel(ordersViewModel: OrdersViewModel): ViewModel

}
