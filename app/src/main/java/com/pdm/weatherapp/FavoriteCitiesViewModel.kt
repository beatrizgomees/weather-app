package com.pdm.weatherapp

import androidx.compose.runtime.toMutableStateList
import androidx.lifecycle.ViewModel
import com.google.android.gms.maps.model.LatLng


class FavoriteCitiesViewModel : ViewModel() {
    private val _cities = getFavoriteCities().toMutableStateList()

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

data class FavoriteCity(val cityName: String, var currentWeather: String, var location: LatLng? = null)


fun getFavoriteCities() = List(30){
        i -> FavoriteCity(cityName = "Cidade $i", currentWeather = "Carregando clima...")
}
