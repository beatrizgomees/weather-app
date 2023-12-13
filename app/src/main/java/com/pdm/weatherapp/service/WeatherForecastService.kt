package com.pdm.weatherapp.service

import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.util.Log
import com.pdm.weatherapp.interfaces.WeatherForecastAPI
import com.pdm.weatherapp.service.WeatherForecastClasses.CurrentWeather
import com.pdm.weatherapp.service.WeatherForecastClasses.Location
import com.pdm.weatherapp.service.WeatherForecastClasses.WeatherForecast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import com.squareup.picasso.Picasso


object WeatherForecastService {
    private var forecastAPI: WeatherForecastAPI
    init {
        val retrofitAPI = Retrofit.Builder()
            .baseUrl(WeatherForecastAPI.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        forecastAPI = retrofitAPI.create(WeatherForecastAPI::class.java)
    }
    fun getName(lat: Double, long: Double, onResponse : (String?) -> Unit ) {
        val call: Call<List<Location>?> = forecastAPI.getName(lat = lat, lng = long)
        enqueue(call) { onResponse.invoke(it.toString()) }

    }
    fun getLocation(name: String, onResponse: (lat: Double?, lon: Double?) -> Unit) {
        val call: Call<List<Location>?> = forecastAPI.getLocation(name)
        enqueue(call) { list ->
            val location = list?.firstOrNull()
            onResponse(location?.lat, location?.lon)
        }
    }

    private fun <T> enqueue(call : Call<T?>, onResponse : ((T?) -> Unit)? = null)
    {
        call.enqueue(object : Callback<T?> {
            override fun onResponse(call: Call<T?>, response: Response<T?>) {
                val obj: T? = response.body()
                onResponse?.invoke(obj)
            }
            override fun onFailure(call: Call<T?>, t: Throwable) {
                Log.w("WeatherApp WARNING", "" + t.message)
            }
        })
    }

    fun getCurrentWeather(name: String, onResponse : (CurrentWeather?) -> Unit) {
        val call: Call<CurrentWeather?> = forecastAPI.getCurrentWeather(name)
        enqueue(call) { onResponse.invoke(it) }
    }
    fun getForecast(name: String, onResponse : (WeatherForecast?) -> Unit) {
        val call: Call<WeatherForecast?> = forecastAPI.getForecast(name)
        enqueue(call) { onResponse.invoke(it) }
    }

    fun getBitmap(imageUrl: String, onResponse: ((Bitmap?) -> Unit)? = null) {
        Picasso.get().load(imageUrl).into(object : com.squareup.picasso.Target {
            override fun onBitmapLoaded(bitmap: Bitmap?, from: Picasso.LoadedFrom?) {
                onResponse?.invoke(bitmap)
            }
            override fun onPrepareLoad(placeHolderDrawable: Drawable?) {}
            override fun onBitmapFailed(e: Exception?, errorDrawable: Drawable?) {
                Log.w("WeatherApp WARNING", "" + e?.message)
                e?.printStackTrace()
            }
        })
    }
}

