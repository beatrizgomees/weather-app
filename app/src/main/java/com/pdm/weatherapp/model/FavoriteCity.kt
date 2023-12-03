package pdm.weatherapp.model

import com.google.android.gms.maps.model.LatLng
import com.google.firebase.firestore.Exclude

data class FavoriteCity (
    var name: String? = null,
    var latitude : Double? = null,
    var longitude : Double? = null,
    @Exclude
    var weatherDesc : String? = null
)