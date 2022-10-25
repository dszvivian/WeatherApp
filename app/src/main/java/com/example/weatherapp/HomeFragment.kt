package com.example.weatherapp

import android.app.Application
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.weatherapp.Extension.toast
import com.example.weatherapp.UnitConversion.convertToCelsius
import com.example.weatherapp.UnitConversion.convertToFahrenheit
import com.example.weatherapp.UnitConversion.getTempUnit
import com.example.weatherapp.ViewModels.MainViewModel
import com.example.weatherapp.databinding.FragmentHomeBinding

class HomeFragment: Fragment(R.layout.fragment_home) {


    private lateinit var binding: FragmentHomeBinding
    private lateinit var tempSettings : TempDisplaySettingManager
    private lateinit var viewModel : MainViewModel


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentHomeBinding.bind(view)

        tempSettings = TempDisplaySettingManager(requireContext())

        val application = Application()

        val getTempData = getTempUnit(requireContext())

        viewModel = ViewModelProvider(this,ViewModelProvider.AndroidViewModelFactory.getInstance(application)).get(MainViewModel::class.java)

        viewModel.getTempByLatAndLon(13.3318 , 74.7454)


        viewModel.responseByLanLonVm.observe(viewLifecycleOwner){ response ->
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

                val iconId = response.body()!!.weather[0].icon

                Glide
                    .with(requireActivity())
                    .load("https://openweathermap.org/img/wn/${iconId}@2x.png")
                    .into(binding.ivHome)

            }else{
                requireActivity().toast("An Error Occurred in Response")
            }
        }


    }


}