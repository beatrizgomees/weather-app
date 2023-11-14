package com.pdm.weatherapp

import androidx.compose.runtime.toMutableStateList
import androidx.lifecycle.ViewModel


class FavoriteCitiesViewModel : ViewModel() {
    private val _cities = getFavoriteCities().toMutableStateList()

    val cities: List<FavoriteCity>
        get() = _cities

    fun remove(city: FavoriteCity){
        _cities.remove(city)
    }

    fun add(city: String){
        _cities.add(FavoriteCity(city, "Carrengando Clima..."))
    }


}

data class FavoriteCity(val cityName: String, var currentWeather: String)


fun getFavoriteCities() = List(30){
        i -> FavoriteCity(cityName = "Cidade $i", currentWeather = "Carregando clima...")
}
