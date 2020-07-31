package com.breakit.driver.ui.map

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.breakit.driver.Common
import com.breakit.driver.R
import com.breakit.driver.databinding.FragmentMapBinding
import com.breakit.driver.utils.extensions.setDataBindingView
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import dagger.android.support.DaggerFragment


class MapFragment : DaggerFragment() {

    private lateinit var binding: FragmentMapBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = setDataBindingView(R.layout.fragment_map, container)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initView()
    }

    private fun initView() {
        binding.deliveryExecutive = Common.orderEnRoute!!.deliveryExecutive!!

        binding.backIv.setOnClickListener {
            findNavController().popBackStack()
        }

        binding.callBtn.setOnClickListener {
            val callIntent = Intent(Intent.ACTION_CALL)
            callIntent.data = Uri.parse("tel:" + Common.orderEnRoute!!.deliveryExecutive!!.mobile)
            startActivity(callIntent)
        }

        // map
        val mapFragment =
            childFragmentManager.findFragmentById(R.id.map_fragment) as SupportMapFragment?
        mapFragment?.getMapAsync(callback)
    }

    private val callback = OnMapReadyCallback { googleMap ->
        val currentLocation =
            LatLng(
                Common.orderEnRoute!!.deliveryExecutive!!.lat!!.toDouble(),
                Common.orderEnRoute!!.deliveryExecutive!!.lon!!.toDouble()
            )
        googleMap.addMarker(MarkerOptions().position(currentLocation).title("Your Meal"))
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(currentLocation))
        googleMap.setMinZoomPreference(15.0f)
    }
}