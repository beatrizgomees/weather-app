package com.pdm.weatherapp.viewmodels

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import pdm.weatherapp.db.FirebaseDB
import pdm.weatherapp.model.FavoriteCity
import pdm.weatherapp.model.User


class MainViewModel : ViewModel() {
    private var _user = mutableStateOf(User("...", "..."))

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

    var user: User
        get() = _user.value
        set(value) {
            _user.value = value
        }

    private val _cities = mutableStateListOf<FavoriteCity>()

    val cities: List<FavoriteCity>
        get() = _cities
}