package com.example.weatherapp

import android.Manifest
import android.app.Application
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import android.os.Bundle
import android.provider.Settings
import android.view.View
import androidx.core.app.ActivityCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.weatherapp.Extension.toast
import com.example.weatherapp.UnitConversion.convertToCelsius
import com.example.weatherapp.UnitConversion.convertToFahrenheit
import com.example.weatherapp.UnitConversion.getTempUnit
import com.example.weatherapp.ViewModels.MainViewModel
import com.example.weatherapp.databinding.FragmentHomeBinding
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices


class HomeFragment : Fragment(R.layout.fragment_home) {


    private lateinit var binding: FragmentHomeBinding
    private lateinit var tempSettings: TempDisplaySettingManager
    private lateinit var viewModel: MainViewModel

    private var latitude:Double = 0.0
    private var longitude:Double = 0.0


    companion object {
        private const val LOCATION_REQUEST_CODE = 100
    }

    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentHomeBinding.bind(view)

        // implements Location settings
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(requireActivity())
        getCurrentLocation()

        tempSettings = TempDisplaySettingManager(requireContext())


        val application = Application()

        val getTempData = getTempUnit(requireContext())


        viewModel = ViewModelProvider(this,
            ViewModelProvider.AndroidViewModelFactory.getInstance(application)).get(MainViewModel::class.java)

        viewModel.getTempByLatAndLon(latitude, longitude)


        viewModel.responseByLanLonVm.observe(viewLifecycleOwner) { response ->
            if (response.isSuccessful && response.body() != null) {

                if (getTempData) {
                    binding.tvHomeTemp.text =
                        "${convertToFahrenheit(response.body()!!.main.temp).toInt()} °F"
                } else {
                    binding.tvHomeTemp.text =
                        "${convertToCelsius(response.body()!!.main.temp).toInt()} °C "
                }

                binding.tvHomePlace.text = response.body()!!.name
                binding.tvHomeSubText.text =
                    "Wind Speed : ${response.body()!!.wind.speed.toString()}"
                binding.tvHomeWeather.text = response.body()!!.weather[0].description

                val iconId = response.body()!!.weather[0].icon

                Glide
                    .with(requireActivity())
                    .load("https://openweathermap.org/img/wn/${iconId}@2x.png")
                    .into(binding.ivHome)

            } else {
                requireActivity().toast("An Error Occurred in Response")
            }
        }

    }

    private fun getCurrentLocation() {

        if (checkPermission()) {

            if (isLocationEnabled()) {

                //set latitude and longitude

                fusedLocationProviderClient.lastLocation.addOnCompleteListener { task ->

                    val location = task.result

                    if(location == null){
                        requireActivity().toast("Location Null Error")
                    }
                    else{
                        requireActivity().toast("Location Fetched")
                        latitude = location.latitude
                        longitude = location.longitude
                    }

                }

            } else {

                // grant location permission ; ie:Settings
                requireActivity().toast("Turn on Location")
                val intent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
                startActivity(intent)

            }


        } else {

            requestPermission()

        }


    }

    private fun requestPermission() {

        ActivityCompat.requestPermissions(requireActivity(),
            arrayOf(
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ),
            LOCATION_REQUEST_CODE
        )
    }

    private fun isLocationEnabled(): Boolean {

        val locationManager: LocationManager =
            requireActivity().getSystemService(Context.LOCATION_SERVICE) as LocationManager

        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(
            LocationManager.NETWORK_PROVIDER)

    }


    private fun checkPermission(): Boolean {

        if (ActivityCompat.checkSelfPermission(requireContext(),
                Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED
            &&
            ActivityCompat.checkSelfPermission(requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
        ) {
            return true
        }



        return false
    }


    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray,
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (requestCode == LOCATION_REQUEST_CODE) {

            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                requireActivity().toast("Permission Granted")
            } else {
                requireActivity().toast("Permission Denied")
            }

        }


    }





}