package com.pdm.weatherapp.service.WeatherForecastClasses

data class CurrentWeather (
    var id : Long? = null,
    var cod : Long? = null,
    var name : String? = null,
    var dt: Long? = 0,
    var base : String? = null,
    var timezone : Long? = null,
    var visibility: Long? = null,
    var coord : Coordinates? = null,
    var weather: Array<Weather>? = null,
    var main : TemperaturePressure? = null,
    var clouds: Map<String, String>? = null,
    var rain: Map<String, Double?>? = null,
    var wind: Map<String, Double?>? = null,
    var snow: Map<String, Double?>? = null,
    var sys: Map<String, String?>? = null,
)