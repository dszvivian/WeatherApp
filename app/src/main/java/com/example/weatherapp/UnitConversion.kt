package com.example.weatherapp

object UnitConversion {

    fun convertToFahrenheit(temp: Double): Double {
        return (1.8*(temp-273)+32)
    }

    fun convertToCelsius(temp: Double): Double {
        return (temp-273.15)
    }


}