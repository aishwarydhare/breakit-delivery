package com.breakit.driver.ui.meal

import com.breakit.driver.data.login.LoginRepository
import com.breakit.driver.ui.common.UiStateViewModel
import javax.inject.Inject

/**
 * ViewModel for login view
 */
class MealDetailsBottomSheetViewModel @Inject constructor(
    private val loginRepository: LoginRepository
) : UiStateViewModel()