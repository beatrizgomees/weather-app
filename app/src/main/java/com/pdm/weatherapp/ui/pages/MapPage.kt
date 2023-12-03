package com.pdm.weatherapp.ui.pages


import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
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
import pdm.weatherapp.db.FirebaseDB
import pdm.weatherapp.model.FavoriteCity
import com.pdm.weatherapp.viewmodels.MainViewModel

@Composable
fun MapPage(modifier: Modifier = Modifier, viewModel: MainViewModel,
            context: Context, camPosState: CameraPositionState
) {
    val recife = LatLng(-8.05, -34.9)
    val caruaru = LatLng(-8.27, -35.98)
    val joaoPessoa = LatLng(-7.12, -34.84)

    val hasLocationPermission by remember {
        mutableStateOf(
            ContextCompat.checkSelfPermission(context,
                Manifest.permission.ACCESS_FINE_LOCATION) ==
                    PackageManager.PERMISSION_GRANTED
        )
    }
    GoogleMap (
        modifier = modifier.fillMaxSize(),
        onMapClick = { FirebaseDB.add(
            FavoriteCity(
            name = "Cidade - lat: ${it.latitude}, long: ${it.longitude}",
            latitude = it.latitude,
            longitude = it.longitude)
        )},
        properties = MapProperties(isMyLocationEnabled = hasLocationPermission),
        uiSettings = MapUiSettings(myLocationButtonEnabled = true),
        cameraPositionState = camPosState,
    ) {
        viewModel.cities.forEach {
            if (it.latitude != null && it.longitude != null) {
                Marker( state = MarkerState(position = LatLng(it.latitude!!, it.longitude!!)),
                    title = it.name, snippet = "${LatLng(it.latitude!!, it.longitude!!)}")
            }
        }

        Marker(
            state = MarkerState(position = recife),
            title = "Recife",
            snippet = "Recife, Pernambuco",
            icon = BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE)
        )

        Marker(
            state = MarkerState(position = caruaru),
            title = "Caruaru",
            snippet = "Caruaru, Pernambuco",
            icon = BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE)
        )

        Marker(
            state = MarkerState(position = joaoPessoa),
            title = "João Pessoa",
            snippet = "João Pessoa, Paraíba",
            icon = BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE)
        )

    }
}

