package com.example.weatherapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import androidx.core.content.ContextCompat
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupWithNavController
import com.example.weatherapp.Extension.toast
import com.example.weatherapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)




        window.statusBarColor = ContextCompat.getColor(this, R.color.dark_sky_blue)

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment
        binding.bnvMain.setupWithNavController(navHostFragment.navController)


//        val navHostFragment = supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment
//        val navController = navHostFragment.navController
//
//
//        binding.bnvMain.setupWithNavController(navController)


//        binding.bnvMain.setOnNavigationItemSelectedListener {
//
//            when(it.itemId){
//
//                R.id.bnv_ItemSearch ->{
//
//                    toast("Feature Coming soon")
//
//                    true
//                }
//
//                R.id.bnv_ItemNews -> {
//
//                    toast("Feature Coming soon")
//
//                    true
//                }
//
//
//                else -> {true}
//            }
//
//        }

    }
}