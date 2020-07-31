package com.breakit.driver.ui.orders

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.breakit.driver.Common
import com.breakit.driver.MainActivity
import com.breakit.driver.R
import com.breakit.driver.databinding.FragmentOrdersBinding
import com.breakit.driver.di.modules.viewmodel.ViewModelFactory
import com.breakit.driver.utils.extensions.observe
import com.breakit.driver.utils.extensions.setDataBindingView
import dagger.android.support.DaggerFragment
import javax.inject.Inject


class OrdersFragment : DaggerFragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val ordersViewModel: OrdersViewModel by viewModels { viewModelFactory }

    private lateinit var binding: FragmentOrdersBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = setDataBindingView(R.layout.fragment_orders, container)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initView()
        initObservers()
    }

    private fun initObservers() {
        ordersViewModel.error.observe(viewLifecycleOwner) {
            Common.handleCommonApiErrorResponse(it, activity as MainActivity)
        }
        ordersViewModel.error.observe(viewLifecycleOwner) {
            Common.handleCommonApiErrorResponse(it, activity as MainActivity)
            if(it != null){
                when(it){

                }
            }
        }
    }

    private fun initView() {
    }

}