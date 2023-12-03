package com.pdm.weatherapp.viewmodels

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.pdm.weatherapp.repository.Repository
import pdm.weatherapp.db.FirebaseDB
import pdm.weatherapp.model.FavoriteCity
import pdm.weatherapp.model.User


class MainViewModel : ViewModel() {
    private val _cities = mutableStateMapOf<String, FavoriteCity>()
    private var _user = mutableStateOf(User("...", "..."))
    val cities: List<FavoriteCity>
        get() = _cities.values.toList()

    var user: User
        get() = _user.value
        set(value) {
            _user.value = value
        }



    init {
        Repository.onUserLogin = { user = it }
        Repository.onUserLogout = { user.name = "..." }
        Repository.onCityAdded = { _cities[it.name!!] = it }
        Repository.onCityRemoved = { _cities.remove(it.name) }
    }


}