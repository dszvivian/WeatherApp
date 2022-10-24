package com.example.weatherapp

import android.app.Activity
import android.widget.Toast

object Extension {

    fun Activity.toast(message:String){
        Toast.makeText(this,message,Toast.LENGTH_LONG).show()
    }

}