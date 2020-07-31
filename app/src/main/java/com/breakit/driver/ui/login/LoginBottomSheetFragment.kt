package com.breakit.driver.ui.login

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.breakit.driver.Common
import com.breakit.driver.MainActivity
import com.breakit.driver.R
import com.breakit.driver.data.login.LoginRepository
import com.breakit.driver.databinding.FragmentLoginBottomSheetBinding
import com.breakit.driver.di.modules.viewmodel.ViewModelFactory
import com.breakit.driver.utils.TextValidation
import com.breakit.driver.utils.extensions.observe
import com.breakit.driver.utils.extensions.setDataBindingView
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dagger.android.support.AndroidSupportInjection
import javax.inject.Inject

class LoginBottomSheetFragment : BottomSheetDialogFragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val loginViewModel: LoginBottomSheetViewModel by viewModels { viewModelFactory }

    @Inject
    lateinit var loginRepository: LoginRepository

    private lateinit var binding: FragmentLoginBottomSheetBinding

    override fun onAttach(context: Context) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = setDataBindingView(R.layout.fragment_login_bottom_sheet, container)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initView()
        initObservers()
    }

    private fun initView() {
        binding.submitBtn.setOnClickListener {
            val mobile = binding.mobileEt.text.toString()
            when {
                loginViewModel.hideOtpLayout.get() -> {
                    if (TextValidation.isValidPhone(mobile)) {
                        loginViewModel.requestOtp(mobile)
                    } else {
                        binding.mobileTil.error = "Invalid Mobile Number"
                    }
                }
                else -> {
                    val otp = binding.otpEt.text.toString()
                    if (TextValidation.isValidOtp(otp)) {
                        loginViewModel.verifyOtp(mobile, otp)
                    } else {
                        binding.otpTil.error = "Invalid OTP"
                    }
                }
            }
        }
    }

    private fun initObservers() {
        binding.viewmodel = loginViewModel
        loginViewModel.error.observe(viewLifecycleOwner) {
            Common.handleCommonApiErrorResponse(it, activity as MainActivity)
            if(it != null){
                when(it){

                }
            }
        }
    }
}