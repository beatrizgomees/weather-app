package com.pdm.weatherapp.repository

import pdm.weatherapp.db.FirebaseDB
import pdm.weatherapp.model.FavoriteCity
import pdm.weatherapp.model.User

object Repository {
    var onUserLogin: ((User) -> Unit)? = null
    var onUserLogout: (() -> Unit)? = null
    var onCityAdded: ((FavoriteCity) -> Unit)? = null
    var onCityRemoved: ((FavoriteCity) -> Unit)? = null
    init {
        FirebaseDB.onUserLogin = { onUserLogin?.invoke(it) }
        FirebaseDB.onUserLogout = { onUserLogout?.invoke() }
        FirebaseDB.onCityAdded = { city -> onCityAdded?.invoke(city) }
        FirebaseDB.onCityRemoved = { onCityRemoved?.invoke(it) }
    }
    fun addCity(name: String) {
        FirebaseDB.add(FavoriteCity(name = name))
    }
    fun addCity(lat: Double, long: Double) {
        FirebaseDB.add(FavoriteCity(latitude = lat, longitude = long))
    }
    fun remove(city: FavoriteCity) {
        FirebaseDB.remove(city)
    }
    fun register(userName: String, email: String) {
        FirebaseDB.register(userName, email)
    }
}