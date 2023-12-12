package com.pdm.weatherapp.service.WeatherForecastClasses

data class Forecast (
    var dt: Long = 0,
    var dt_txt: String? = null,
    var main: TemperaturePressure? = null,
    var weather: Array<Weather>? = null,
    var clouds: Map<String, String>? = null,
    var wind: Map<String, Double?>? = null,
    var visibility: Long? = null,
    var pop: Double? = null,
    var rain: Map<String, Double?>? = null,
    var sys: Map<String, String?>? = null,
)