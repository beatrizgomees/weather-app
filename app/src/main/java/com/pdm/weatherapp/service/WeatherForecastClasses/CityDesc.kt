package com.pdm.weatherapp.service.WeatherForecastClasses

data class CityDesc (
    var id: String? = null,
    var name: String? = null,
    var country: String? = null,
    var population: Long = 0,
    var timezone: Long = 0,
    var coord: Coordinates? = null
)