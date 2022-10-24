package com.example.weatherapp.ViewModels

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherapp.Extension.toast
import com.example.weatherapp.RetrofitInstance
import com.example.weatherapp.UnitConversion
import com.example.weatherapp.models.CurrentWeatherModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import retrofit2.Response

class MainViewModel(application: Application) : AndroidViewModel(application) {

    val responseVm = MutableLiveData<Response<CurrentWeatherModel>>()

    fun getTempByLatAndLon(lat:Double , lon:Double) = viewModelScope.launch {
        val response = try{
                RetrofitInstance.api.getWeatherByLatAndLon(lat, lon)
        }catch (e:Exception){
            Log.e("ERROR", e.message!!)
            return@launch
        }

        responseVm.postValue(response)

    }

}