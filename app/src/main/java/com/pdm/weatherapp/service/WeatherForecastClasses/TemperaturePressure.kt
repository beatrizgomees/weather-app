package com.pdm.weatherapp.service.WeatherForecastClasses

data class TemperaturePressure (
    var temp : Double? = null,
    var feels_like : Double? = null,
    var temp_min : Double? = null,
    var temp_max : Double? = null,
    var temp_kf : Double? = null,
    var pressure : Double? = null,
    var sea_level : Double? = null,
    var grnd_level : Double? = null
)