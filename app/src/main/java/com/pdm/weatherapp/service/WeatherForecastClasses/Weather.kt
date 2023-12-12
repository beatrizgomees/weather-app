package com.pdm.weatherapp.service.WeatherForecastClasses

data class Weather (
    var id: String? = null,
    var main: String? = null,
    var description: String? = null,
    var icon: String? = null
)