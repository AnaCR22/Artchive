package com.uo300568.artchive.ui.auth

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.google.firebase.auth.FirebaseAuth
import com.uo300568.artchive.MainActivity
import com.uo300568.artchive.R

class AuthActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()
        setContentView(R.layout.activity_auth)

        val currentUser = FirebaseAuth.getInstance().currentUser
        if (currentUser != null) { //ya está logueado
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}