package com.breakit.delivery.di.bindings

import androidx.lifecycle.ViewModel
import com.breakit.delivery.MainViewModel
import com.breakit.delivery.di.modules.viewmodel.ViewModelKey
import com.breakit.delivery.ui.home.HomeViewModel
import com.breakit.delivery.ui.login.LoginBottomSheetViewModel
import com.breakit.delivery.ui.meal.MealDetailsBottomSheetViewModel
import com.breakit.delivery.ui.orders.OrdersViewModel
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
