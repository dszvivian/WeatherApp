package com.example.weatherapp

import android.app.Application
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.weatherapp.Extension.toast
import com.example.weatherapp.UnitConversion.convertToCelsius
import com.example.weatherapp.UnitConversion.convertToFahrenheit
import com.example.weatherapp.ViewModels.MainViewModel
import com.example.weatherapp.databinding.FragmentHomeBinding
import com.example.weatherapp.models.CurrentWeatherModel
import retrofit2.Response

class HomeFragment: Fragment(R.layout.fragment_home) {


    private lateinit var binding: FragmentHomeBinding
    private lateinit var tempSettings : TempDisplaySettingManager
    private lateinit var viewModel : MainViewModel


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentHomeBinding.bind(view)

        tempSettings = TempDisplaySettingManager(requireContext())

        val application = Application()

        val getTempData = getTempUnit()

        viewModel = ViewModelProvider(this,ViewModelProvider.AndroidViewModelFactory.getInstance(application)).get(MainViewModel::class.java)

        viewModel.getTempByLatAndLon(13.3318 , 74.7454)


        viewModel.responseVm.observe(viewLifecycleOwner){response ->
            if(response.isSuccessful  && response.body() != null){

                if(getTempData){
                    binding.tvHomeTemp.text = "${convertToFahrenheit(response.body()!!.main.temp).toInt()} °F"
                }
                else{
                    binding.tvHomeTemp.text = "${convertToCelsius(response.body()!!.main.temp).toInt()} °C "
                }

                binding.tvHomePlace.text = response.body()!!.name
                binding.tvHomeSubText.text = "Wind Speed : ${response.body()!!.wind.speed.toString()}"
                binding.tvHomeWeather.text = response.body()!!.weather[0].description
            }else{
                requireActivity().toast("An Error Occurred in Response")
            }
        }


    }

    private fun getTempUnit(): Boolean {

        return tempSettings.getTempDisplaySettings().name != TempDisplaySettings.Celsius.name


    }


}