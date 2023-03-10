package com.example.movieproject.ui


import android.os.Bundle

import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.example.movieproject.R
import com.example.movieproject.databinding.ActivityMainBinding
import com.example.movieproject.model.Game
import com.google.android.material.bottomnavigation.BottomNavigationView



class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private  lateinit var bottomNav: BottomNavigationView
    private  lateinit var navController: NavController


    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        bottomNav= binding.mBottomNavigationView
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host) as NavHostFragment
        navController = navHostFragment.navController

        replaceFragment(MainFragment())
        binding.mBottomNavigationView.setOnItemSelectedListener {

            when(it.itemId){

                R.id.home_icon -> replaceFragment(MainFragment())
                R.id.fav_icon -> replaceFragment(FavoriteFragment())
                else ->{
                }
            }

            true
        }


    }



    private fun replaceFragment(fragment : Fragment){
//


        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
//                R.id.splashFragment -> hideBottomNav()
                R.id.detailFragment -> hideBottomNav()
                else -> showBottomNav()
            }
        }
        fragmentTransaction.replace(R.id.nav_host,fragment)
//        fragmentManager.remo
        fragmentTransaction.commit()


    }

        private fun hideBottomNav(){
        bottomNav.isVisible = false
    }

    //
    private fun showBottomNav(){
        bottomNav.isVisible = true
    }

}
