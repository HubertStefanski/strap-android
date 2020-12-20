package com.hstefans.strap_android.fragments

import androidx.fragment.app.Fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.hstefans.strap_android.R

class MapsFragment : Fragment(), OnMapReadyCallback, GoogleMap.OnMarkerClickListener {
    private lateinit var map: GoogleMap
    private lateinit var fusedLocationClient: FusedLocationProviderClient


    private val callback = OnMapReadyCallback { map ->
        val myPlace = LatLng(52.245653492419805, -7.139291147881547)  // WIT
        map.addMarker(MarkerOptions().position(myPlace).title("WIT"))
        map.moveCamera(CameraUpdateFactory.newLatLng(myPlace))
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this.requireContext().applicationContext)

        return inflater.inflate(R.layout.fragment_maps, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(callback)
    }

    override fun onMapReady(p0: GoogleMap?) {
        map.uiSettings.isZoomControlsEnabled = true
        map.setOnMarkerClickListener(this)

    }

//    override fun onMarkerClick(p0: Marker?): Boolean {
//        TODO("Not yet implemented")
//    }

    override fun onMarkerClick(p0: Marker?) = false
}