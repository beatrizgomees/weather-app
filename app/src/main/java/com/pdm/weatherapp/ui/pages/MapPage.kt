package com.pdm.weatherapp.ui.pages


import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.widget.Toast
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf

import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.core.content.ContextCompat
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.CameraPositionState
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.MapUiSettings
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.pdm.weatherapp.repository.Repository
import com.pdm.weatherapp.service.WeatherForecastService
import pdm.weatherapp.db.FirebaseDB
import pdm.weatherapp.model.FavoriteCity
import com.pdm.weatherapp.viewmodels.MainViewModel

@Composable
fun MapPage(modifier: Modifier = Modifier, viewModel: MainViewModel,
            context: Context, camPosState: CameraPositionState
) {
    val hasLocationPermission by remember {
        mutableStateOf(
            ContextCompat.checkSelfPermission(context,
                Manifest.permission.ACCESS_FINE_LOCATION) ==
                    PackageManager.PERMISSION_GRANTED
        )
    }
    GoogleMap (
        modifier = modifier.fillMaxSize(),
        uiSettings = MapUiSettings(myLocationButtonEnabled = true),
        cameraPositionState = camPosState,
        properties = MapProperties(isMyLocationEnabled = hasLocationPermission),
        onMapClick = {
            Repository.addCity(lat = it.latitude, long = it.longitude)
            Toast.makeText(context, "Toque no mapa", Toast.LENGTH_SHORT).show()
        }

    ) {
        viewModel.cities.forEach {
                city ->
            val marker = if (city.bitmap != null)
                BitmapDescriptorFactory.fromBitmap(city.bitmap!!)
            else BitmapDescriptorFactory.defaultMarker()
            var desc = "${city.latitude}:${city.longitude}"
            city.currentWeather?.let { w ->
                desc = "${w.weather?.get(0)?.description} ${w.main?.temp}℃"
            }
            if (city.latitude != null && city.longitude != null) {
                Marker( state = MarkerState(
                    position = LatLng(city.latitude!!, city.longitude!!)),
                    title = city.name, snippet = desc, icon= marker, onClick = {_ ->
                        if (city.bitmap == null && city.imageUrl != null) {
                            WeatherForecastService.getBitmap(city.imageUrl!!) {
                                city.bitmap = it
                                Repository.onCityUpdated?.invoke(city)
                            }
                        }
                        return@Marker false
                    }
                )


            }
        }



    }
}

