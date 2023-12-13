package com.pdm.weatherapp.repository

import com.pdm.weatherapp.service.WeatherForecastService
import pdm.weatherapp.db.FirebaseDB
import pdm.weatherapp.model.FavoriteCity
import pdm.weatherapp.model.User


object Repository {
    var onUserLogin: ((User) -> Unit)? = null
    var onUserLogout: (() -> Unit)? = null
    var onCityAdded: ((FavoriteCity) -> Unit)? = null
    var onCityRemoved: ((FavoriteCity) -> Unit)? = null
    var onCityUpdated: ((FavoriteCity) -> Unit)? = null
    init {
        FirebaseDB.onUserLogin = { onUserLogin?.invoke(it) }
        FirebaseDB.onUserLogout = { onUserLogout?.invoke() }
        FirebaseDB.onCityAdded = { city ->
            onCityAdded?.invoke(city)
            WeatherForecastService.getCurrentWeather(city.name!!) { currWeather ->
                currWeather?.let { cw ->
                    city.currentWeather = cw
                    onCityUpdated?.invoke(city)
                    cw.weather?.get(0)?.let { w ->
                        val imgFile = getImageFileName(w.icon)
                        FirebaseDB.getFileURL(imgFile) { url ->
                            city.imageUrl = url
                            onCityUpdated?.invoke(city)
                            if (url != null) {
                                WeatherForecastService.getBitmap(url) {
                                    city.bitmap = it
                                    onCityUpdated?.invoke(city)
                                }
                            }
                        }
                    }
                }
            }
        }
        FirebaseDB.onCityRemoved = { onCityRemoved?.invoke(it) }
    }
    fun addCity(name : String) {
        WeatherForecastService.getLocation(name) { lat, long ->
            FirebaseDB.add(FavoriteCity(name, lat?:0.0, long?:0.0))
        }
    }
    fun addCity(lat: Double, long: Double) {
        WeatherForecastService.getName(lat, long){ name ->
            val cityName = name ?: ("Erro@$lat:$long")
            FirebaseDB.add(FavoriteCity(cityName, lat, long))
        }
    }
    fun remove(city: FavoriteCity) {
        FirebaseDB.remove(city)
    }
    fun register(userName: String, email: String) {
        FirebaseDB.register(userName, email)
    }
    fun loadForecast(city : FavoriteCity) {
        WeatherForecastService.getForecast(city.name!!) {
            city.forecast = it
            onCityUpdated?.invoke(city)
        }
    }

    fun getImageFileName(icon: String?): String {
        val icon = icon ?: "13d" // default/error = snow
        return "img/$icon@4x.png"
    }

}