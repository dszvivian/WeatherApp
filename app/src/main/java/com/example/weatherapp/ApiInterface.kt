package com.example.weatherapp

import com.example.weatherapp.models.CurrentWeatherModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface  ApiInterface {


        @GET("data/2.5/weather")
    suspend fun getWeatherByLatAndLon(
        @Query("lat") lat: Double,
        @Query("lon") lon: Double,
        @Query("appid") apiKey:String = "652a358cdd742886e879118383a4ed89"
    )  : Response<CurrentWeatherModel>



    @GET("data/2.5/weather")
    suspend fun getWeatherByCity(
        @Query("q") city_name: String,
        @Query("appid") apiKey:String = "652a358cdd742886e879118383a4ed89"
    )  : Response<CurrentWeatherModel>

}