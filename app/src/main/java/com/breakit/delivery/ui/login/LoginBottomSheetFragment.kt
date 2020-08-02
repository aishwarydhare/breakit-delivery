package com.breakit.delivery.ui.login

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.breakit.delivery.Common
import com.breakit.delivery.MainActivity
import com.breakit.delivery.R
import com.breakit.delivery.data.login.LoginRepository
import com.breakit.delivery.databinding.FragmentLoginBottomSheetBinding
import com.breakit.delivery.di.modules.viewmodel.ViewModelFactory
import com.breakit.delivery.utils.TextValidation
import com.breakit.delivery.utils.extensions.observe
import com.breakit.delivery.utils.extensions.setDataBindingView
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