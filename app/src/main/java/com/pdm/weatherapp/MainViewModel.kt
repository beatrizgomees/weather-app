package com.pdm.weatherapp

import FavoriteCity
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.toMutableStateList
import androidx.lifecycle.ViewModel
import com.google.android.gms.maps.model.LatLng
import com.pdm.weatherapp.db.FirebaseDB
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
    private val _cities = getFavoriteCities().toMutableStateList()


    private var _user = mutableStateOf(User("...", "..."))
    var user : User
        get() = _user.value
        set(tmp) {
            _user.value.name = tmp.name
            _user.value.email = tmp.email
        }

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


}




fun getFavoriteCities() = List(30){
        i -> FavoriteCity(cityName = "Cidade $i", currentWeather = "Carregando clima...")
}
