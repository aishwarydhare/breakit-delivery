package com.breakit.delivery.ui.meal

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.breakit.delivery.Common
import com.breakit.delivery.R
import com.breakit.delivery.databinding.FragmentMealDetailsBottomSheetBinding
import com.breakit.delivery.model.Order
import com.breakit.delivery.utils.async.ThreadManager
import com.breakit.delivery.utils.extensions.setDataBindingView
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dagger.android.support.AndroidSupportInjection
import javax.inject.Inject

private const val MODE = "MODE"

class MealDetailsBottomSheetFragment : BottomSheetDialogFragment() {

    var order: Order? = null

    @Inject
    lateinit var threadManager: ThreadManager

    private lateinit var binding: FragmentMealDetailsBottomSheetBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        order = Common.orderEnRoute
    }

    override fun onAttach(context: Context) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = setDataBindingView(R.layout.fragment_meal_details_bottom_sheet, container)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initView()
    }

    private fun initView() {
        binding.order = order
        binding.callBtn.setOnClickListener {
            val callIntent = Intent(Intent.ACTION_VIEW)
            callIntent.data = Uri.parse("tel:" + order!!.customerMobile!!)
            startActivity(callIntent)
        }
        binding.startNavigationBtn.setOnClickListener {
            val slat = Common.slat
            val slon = Common.slon
            val dlat = order!!.customer!!.lat
            val dlon = order!!.customer!!.lon
            val intent = Intent(
                Intent.ACTION_VIEW,
                Uri.parse("http://maps.google.com/maps?saddr=$slat,$slon&daddr=$dlat,$dlon")
            )
            startActivity(intent)
        }
    }
}