package com.example.weatherapp

import android.app.Application
import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.weatherapp.Extension.toast
import com.example.weatherapp.UnitConversion.getTempUnit
import com.example.weatherapp.ViewModels.MainViewModel
import com.example.weatherapp.databinding.FragmentSearchcityBinding

class SearchFragment : Fragment(R.layout.fragment_searchcity) {


    private lateinit var binding: FragmentSearchcityBinding
    private lateinit var viewModel: MainViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val application = Application()

        binding = FragmentSearchcityBinding.bind(view)

        viewModel = ViewModelProvider(this,
            ViewModelProvider.AndroidViewModelFactory.getInstance(application)).get(MainViewModel::class.java)



        binding.btnSearch.setOnClickListener {

            val city = binding.etSearchCity.text.toString()

            viewModel.getTempByCity(city)


            viewModel.responseByCityVm.observe(viewLifecycleOwner) { response ->

                if (response.isSuccessful && response.body() != null) {

                    if (getTempUnit(requireContext())) {
                        binding.tvSearchTemp.text = "${
                            UnitConversion.convertToFahrenheit(response.body()!!.main.temp).toInt()
                        } °F"
                    } else {
                        binding.tvSearchTemp.text = "${
                            UnitConversion.convertToCelsius(response.body()!!.main.temp).toInt()
                        } °C "
                    }

                    binding.tvSearchPlace.text = response.body()!!.name
                    binding.tvSearchSubText.text =
                        "Wind Speed : ${response.body()!!.wind.speed.toString()}"
                    binding.tvSearchWeather.text = response.body()!!.weather[0].description

                    val iconId = response.body()!!.weather[0].icon

                    Glide
                        .with(requireActivity())
                        .load("https://openweathermap.org/img/wn/${iconId}@2x.png")
                        .into(binding.ivSearch)

                } else {
                    requireActivity().toast("An Error Occurred in Response")
                }


                val imm =
                    requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                imm.hideSoftInputFromWindow(view.windowToken, 0)


            }


        }


    }


}