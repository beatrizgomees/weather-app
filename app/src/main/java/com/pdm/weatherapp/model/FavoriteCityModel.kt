import com.google.android.gms.maps.model.LatLng

data class FavoriteCity(val cityName: String, var currentWeather: String, var location: LatLng? = null)