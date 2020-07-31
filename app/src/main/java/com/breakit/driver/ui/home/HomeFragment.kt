package com.breakit.driver.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.breakit.driver.Common
import com.breakit.driver.MainActivity
import com.breakit.driver.R
import com.breakit.driver.databinding.FragmentHomeBinding
import com.breakit.driver.di.modules.viewmodel.ViewModelFactory
import com.breakit.driver.utils.extensions.observe
import com.breakit.driver.utils.extensions.setDataBindingView
import com.breakit.driver.utils.extensions.showErrorSnackbar
import dagger.android.support.DaggerFragment
import javax.inject.Inject

class HomeFragment : DaggerFragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val homeViewModel: HomeViewModel by viewModels { viewModelFactory }

    private lateinit var binding: FragmentHomeBinding

    private var deliveryAdapter: DeliveryAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = setDataBindingView(R.layout.fragment_home, container)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initView()
        initObservers()
        loadData()
    }

    private fun loadData() {
        homeViewModel.getTodayDeliveriesFromRemote()
    }

    private fun initView() {
        binding.viewmodel = homeViewModel

        binding.pullToRefresh.setOnRefreshListener {
            loadData()
        }

        deliveryAdapter = DeliveryAdapter(
            orders = homeViewModel.todaysDeliveries.value!!,
            handleOnViewClick = { indexClicked ->
                Common.orderEnRoute = homeViewModel.todaysDeliveries.value!![indexClicked]
                (activity as MainActivity).showMealDetailFragment()
            },
            handleOnPickUpClick = { indexClicked ->
                val order = homeViewModel.todaysDeliveries.value!![indexClicked]
                homeViewModel.pickUpDelivery(order, activity = activity as MainActivity)
            },
            handleOnCompleteClick = { indexClicked ->
                val order = homeViewModel.todaysDeliveries.value!![indexClicked]
                homeViewModel.completeDelivery(order, activity = activity as MainActivity)
            },
            handleOnAbortClick = { indexClicked ->
                val order = homeViewModel.todaysDeliveries.value!![indexClicked]
                homeViewModel.abortDelivery(order, activity = activity as MainActivity)
            }
        )

        with(binding.deliveriesRv) {
            layoutManager = object : LinearLayoutManager(requireContext()) {
                override fun canScrollVertically(): Boolean = false
            }
            adapter = deliveryAdapter
        }
    }

    private fun initObservers() {
        binding.viewmodel = homeViewModel
        homeViewModel.todaysDeliveries.observe(viewLifecycleOwner) {
            binding.deliveriesExist = it != null && it.isNotEmpty()
            binding.pullToRefresh.isRefreshing = false
            if (it != null) {
                deliveryAdapter!!.setOrders(it)
            }
            deliveryAdapter!!.notifyDataSetChanged()
        }
        homeViewModel.error.observe(viewLifecycleOwner) {
            Common.handleCommonApiErrorResponse(it, activity as MainActivity)
            if (it != null) {
                when (it) {
                    -1, -2, -3, -4 -> (activity as MainActivity).showErrorSnackbar("something went wrong, $it")
                }
            }
        }
    }

}