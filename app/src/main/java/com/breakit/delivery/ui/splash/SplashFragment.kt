package com.breakit.delivery.ui.splash

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.breakit.delivery.R
import com.breakit.delivery.databinding.FragmentSplashBinding
import com.breakit.delivery.utils.extensions.setDataBindingView

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