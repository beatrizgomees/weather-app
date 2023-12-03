package com.pdm.weatherapp.ui.activities

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.google.firebase.auth.auth
import com.pdm.weatherapp.ui.pages.RegisterPage
import com.pdm.weatherapp.ui.theme.WeatherAppTheme
import com.pdm.weatherapp.utils.FBAuthListener

class RegisterActivity : ComponentActivity() {
    private lateinit var fbAuthListener: FBAuthListener

    override fun onStart() {
        super.onStart()
        com.google.firebase.Firebase.auth.addAuthStateListener(fbAuthListener)
    }
    override fun onStop() {
        super.onStop()
        com.google.firebase.Firebase.auth.removeAuthStateListener(fbAuthListener)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.fbAuthListener = FBAuthListener(this)
        setContent {
            WeatherAppTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    RegisterPage()
                }
            }
        }
    }
}