package com.pdm.weatherapp.service.WeatherForecastClasses

data class WeatherForecast (
    var cod: String? = null,
    var message: Float? = null,
    var cnt: Int? = null,
    var list: List<Forecast>? = null,
    var city: CityDesc? = null
)