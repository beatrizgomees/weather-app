package com.pdm.weatherapp.service.WeatherForecastClasses

data class Location (
    var name : String? = null,
    var local_names : Map<String, String>? = null,
    var lat : Double? = null,
    var lon: Double? = null,
    var country : String?= null
)