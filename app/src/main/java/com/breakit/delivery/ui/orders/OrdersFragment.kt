package com.breakit.delivery.ui.orders

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.breakit.delivery.Common
import com.breakit.delivery.MainActivity
import com.breakit.delivery.R
import com.breakit.delivery.databinding.FragmentOrdersBinding
import com.breakit.delivery.di.modules.viewmodel.ViewModelFactory
import com.breakit.delivery.utils.extensions.observe
import com.breakit.delivery.utils.extensions.setDataBindingView
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