package pdm.weatherapp.model

import com.google.android.gms.maps.model.LatLng

data class FavoriteCity(
    var name: String? = null,
    var weather: String? = null,
    var latitude: Double? = null,
    var longitude: Double? = null,
)