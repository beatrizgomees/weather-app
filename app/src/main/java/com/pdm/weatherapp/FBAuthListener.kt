package com.pdm.weatherapp

import android.app.Activity
import android.content.Intent
import com.google.firebase.auth.FirebaseAuth

class FBAuthListener(private val activity: Activity) : FirebaseAuth.AuthStateListener {
    override fun onAuthStateChanged(firebaseAuth: FirebaseAuth) {
        val user = firebaseAuth.currentUser
        var intent: Intent? = null
        if (user != null && activity !is HomeActivity) {
            intent = Intent(activity, HomeActivity::class.java)
        }
        if (user == null && activity is HomeActivity) {
            intent = Intent(activity, LoginActivity::class.java)
        }
        if (intent != null) {
            activity.startActivity(intent)
            activity.finish()
        }
    }
}