package com.breakit.driver.ui.splash

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.breakit.driver.R
import com.breakit.driver.databinding.FragmentSplashBinding
import com.breakit.driver.utils.extensions.setDataBindingView

class SplashFragment : Fragment() {

    private lateinit var binding: FragmentSplashBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = setDataBindingView(R.layout.fragment_splash, container)
        return binding.root
    }

    companion object {
        @JvmStatic
        fun newInstance() = SplashFragment()
    }
}