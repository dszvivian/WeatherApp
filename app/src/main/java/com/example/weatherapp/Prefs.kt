package com.example.weatherapp

import android.content.Context

enum class TempDisplaySettings {
    Fahrenheit, Celsius
}

class TempDisplaySettingManager(context: Context) {

    private val preferences = context.getSharedPreferences("settings", Context.MODE_PRIVATE)

    fun updateSetting(setting: TempDisplaySettings) {
        preferences.edit().putString("key_temp_display", setting.name).apply()
    }

    fun getTempDisplaySettings(): TempDisplaySettings {
        val settingValue =
            preferences.getString("key_temp_display", TempDisplaySettings.Fahrenheit.name)
                ?: TempDisplaySettings.Fahrenheit.name
        return TempDisplaySettings.valueOf(settingValue)
    }


}