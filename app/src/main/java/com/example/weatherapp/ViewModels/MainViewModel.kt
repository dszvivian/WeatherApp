package com.example.weatherapp.ViewModels

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.weatherapp.RetrofitInstance
import com.example.weatherapp.models.CurrentWeatherModel
import kotlinx.coroutines.launch
import retrofit2.Response

class MainViewModel(application: Application) : AndroidViewModel(application) {

    val responseByLanLonVm = MutableLiveData<Response<CurrentWeatherModel>>()
    val responseByCityVm = MutableLiveData<Response<CurrentWeatherModel>>()

    fun getTempByLatAndLon(lat:Double , lon:Double) = viewModelScope.launch {
        val response = try{
                RetrofitInstance.api.getWeatherByLatAndLon(lat, lon)
        }catch (e:Exception){
            Log.e("ERROR", e.message!!)
            return@launch
        }

        responseByLanLonVm.postValue(response)

    }

    fun getTempByCity(city:String) = viewModelScope.launch {
        val response = try{
            RetrofitInstance.api.getWeatherByCity(city)
        }catch (e:Exception){
            Log.e("ERROR", e.message!!)
            return@launch
        }
        responseByCityVm.postValue(response)
    }



}