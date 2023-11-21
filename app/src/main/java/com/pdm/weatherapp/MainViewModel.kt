package com.pdm.weatherapp

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.google.android.gms.maps.model.LatLng
import com.pdm.weatherapp.db.FirebaseDB
import com.pdm.weatherapp.model.FavoriteCity
import com.pdm.weatherapp.model.User


class MainViewModel : ViewModel() {

    init {
        FirebaseDB.onUserLogin = {
            user = it
        }
        FirebaseDB.onUserLogout = {
            user.name = "..."
        }
        FirebaseDB.onCityAdded = {
            _cities.add(it)
        }
        FirebaseDB.onCityRemoved = {
            _cities.remove(it)
        }
    }
    private val _cities = mutableStateListOf<FavoriteCity>()

    val cities: List<FavoriteCity>
        get() = _cities



    private var _user = mutableStateOf(User("...", "..."))
    var user : User
        get() = _user.value
        set(tmp) {
            _user.value.name = tmp.name
            _user.value.email = tmp.email
        }
}



