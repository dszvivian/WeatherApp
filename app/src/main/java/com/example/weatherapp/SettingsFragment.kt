package com.example.weatherapp

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.example.weatherapp.Extension.toast
import com.example.weatherapp.databinding.FragmentSettingsBinding

private lateinit var binding : FragmentSettingsBinding

class SettingsFragment : Fragment(R.layout.fragment_settings) {

    private lateinit var tempSettings:TempDisplaySettingManager

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        tempSettings = TempDisplaySettingManager(requireContext())


        binding = FragmentSettingsBinding.bind(view)


        binding.isF.isChecked = getTempUnit() == true






        binding.btnSettingsSave.setOnClickListener {

            val isF = binding.isF.isChecked

            if(isF) {
                tempSettings.updateSetting(TempDisplaySettings.Fahrenheit)
            }
            else{
                tempSettings.updateSetting(TempDisplaySettings.Celsius)
            }

            requireActivity().toast("Settings saved")

        }

    }

    private fun getTempUnit(): Boolean {

        return tempSettings.getTempDisplaySettings().name != TempDisplaySettings.Celsius.name


    }

}