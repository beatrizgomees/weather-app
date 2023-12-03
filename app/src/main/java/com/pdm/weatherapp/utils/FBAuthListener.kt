package com.pdm.weatherapp.utils

import android.app.Activity
import android.content.Intent
import com.google.firebase.auth.FirebaseAuth
import com.pdm.weatherapp.ui.activities.LoginActivity

import com.pdm.weatherapp.ui.activities.MainActivity


class FBAuthListener(private val activity: Activity) : FirebaseAuth.AuthStateListener {
    override fun onAuthStateChanged(firebaseAuth: FirebaseAuth) {
        val user = firebaseAuth.currentUser
        var intent: Intent? = null
        if (user != null && activity !is MainActivity) {
            intent = Intent(activity, MainActivity::class.java)
        }
        if (user == null && activity is MainActivity) {
            intent = Intent(activity, LoginActivity::class.java)
        }
        if (intent != null) {
            activity.startActivity(intent)
            activity.finish()
        }
    }
}