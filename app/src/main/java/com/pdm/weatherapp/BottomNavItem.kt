package com.pdm.weatherapp


import androidx.activity.ComponentActivity
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Lock
import androidx.compose.ui.graphics.vector.ImageVector


sealed class BottomNavItem(var title: String, var icon: ImageVector, var route: String){

    object HomePage : BottomNavItem("Inicio", Icons.Default.Home, "home")
    object ListPage : BottomNavItem("Lista", Icons.Default.List, "list")
    object MapPage : BottomNavItem("Mapa", Icons.Default.LocationOn, "map")

}

