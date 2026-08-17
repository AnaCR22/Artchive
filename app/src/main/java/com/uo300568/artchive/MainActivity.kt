package com.uo300568.artchive

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.fragment.NavHostFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.navigation.findNavController

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()
        setContentView(R.layout.activity_main)

        val bottomNavView = findViewById<BottomNavigationView>(R.id.bottomNavMain)
        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.fragmentContainerView) as NavHostFragment
        val navController = navHostFragment.navController

        navController.addOnDestinationChangedListener { _, destination, _ ->
            if (destination.id == R.id.cuadroFragment) {
                return@addOnDestinationChangedListener
            }
        }
        bottomNavView.setOnItemSelectedListener { item ->
            navController.popBackStack(navController.graph.startDestinationId, false)
            navController.navigate(item.itemId)
            true
        }

    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.fragmentContainerView)
        return navController.navigateUp() || super.onSupportNavigateUp()
    }

}