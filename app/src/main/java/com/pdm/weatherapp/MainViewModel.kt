package com.pdm.weatherapp

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.google.android.gms.maps.model.LatLng
import com.pdm.weatherapp.model.FavoriteCity
import com.pdm.weatherapp.model.User


class MainViewModel : ViewModel() {
    private val _cities = mutableStateListOf<FavoriteCity>()

    val cities: List<FavoriteCity>
        get() = _cities

    fun remove(city: FavoriteCity){
        _cities.remove(city)
    }

    fun add(city: String, location: LatLng? = null){
        _cities.add(
            FavoriteCity(
                city, "Carregando clima...", location = location
            )
        )
    }

    private var _user = mutableStateOf(User("...", "..."))
    var user : User
        get() = _user.value
        set(tmp) {
            _user.value.name = tmp.name
            _user.value.email = tmp.email
        }
}



fun getFavoriteCities() = List(30){
        i -> FavoriteCity(name = "Cidade $i", weather  = "Carregando clima...")
}
