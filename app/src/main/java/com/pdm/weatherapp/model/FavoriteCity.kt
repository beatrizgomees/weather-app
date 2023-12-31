package pdm.weatherapp.model

import android.graphics.Bitmap
import com.google.android.gms.maps.model.LatLng
import com.google.firebase.firestore.Exclude
import com.pdm.weatherapp.service.WeatherForecastClasses.CurrentWeather
import com.pdm.weatherapp.service.WeatherForecastClasses.WeatherForecast

data class FavoriteCity (
    var name: String? = null,
    var latitude : Double? = null,
    var longitude : Double? = null,
    @Exclude
    var imageUrl : String? = null,
    @Exclude
    var bitmap : Bitmap? = null,
    @Exclude
    var currentWeather: CurrentWeather? = null,
    @Exclude
    var forecast: WeatherForecast? = null
)