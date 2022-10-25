package com.example.weatherapp

import android.content.Context

object UnitConversion {



    fun convertToFahrenheit(temp: Double): Double {
        return (1.8*(temp-273)+32)
    }

    fun convertToCelsius(temp: Double): Double {
        return (temp-273.15)
    }

    fun getTempUnit(context:Context): Boolean {
        val tempSettings = TempDisplaySettingManager(context)
        return tempSettings.getTempDisplaySettings().name != TempDisplaySettings.Celsius.name
    }


}