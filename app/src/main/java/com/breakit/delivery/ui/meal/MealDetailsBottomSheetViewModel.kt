package com.breakit.delivery.ui.meal

import com.breakit.delivery.data.login.LoginRepository
import com.breakit.delivery.ui.common.UiStateViewModel
import javax.inject.Inject

/**
 * ViewModel for login view
 */
class MealDetailsBottomSheetViewModel @Inject constructor(
    private val loginRepository: LoginRepository
) : UiStateViewModel()