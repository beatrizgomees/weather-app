package com.pdm.weatherapp.ui.components

import android.content.Context
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost

import androidx.navigation.compose.currentBackStackEntryAsState
import com.google.maps.android.compose.rememberCameraPositionState
import com.pdm.weatherapp.ui.pages.HomePage
import com.pdm.weatherapp.ui.pages.ListPage
import com.pdm.weatherapp.ui.pages.MapPage
import com.pdm.weatherapp.viewmodels.MainViewModel

@Composable
fun BottomNavBar(navController: NavHostController) {
    val items = listOf(
        BottomNavItem.HomePage,
        BottomNavItem.ListPage,
        BottomNavItem.MapPage,
    )
    NavigationBar(
        contentColor = Color.Black
    ) {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route
        items.forEach { item ->
            NavigationBarItem (
                icon = { Icon(imageVector = item.icon, contentDescription= item.title)},
                label = { Text(text = item.title, fontSize = 12.sp) },
                alwaysShowLabel = true,
                selected = currentRoute == item.route,
                onClick = {
                    navController.navigate(item.route) {
// Navegação simplificada

                        navController.graph.startDestinationRoute?.let {

                            popUpTo(it)
                        }

                        launchSingleTop = true

                    }
                }
            )
        }
    }
}

@Composable
fun MainNavHost(
    navController: NavHostController,
    viewModel: MainViewModel,
    context: Context
) {
    val cameraPositionState = rememberCameraPositionState()

    NavHost(navController = navController, startDestination = BottomNavItem.HomePage.route) {
        composable(route = BottomNavItem.HomePage.route) {
            HomePage(context = context, viewModel = viewModel)
        }
        composable(route = BottomNavItem.ListPage.route) {
            ListPage(modifier = Modifier, context = context, viewModel = viewModel)
        }
        composable(route = BottomNavItem.MapPage.route) {
            MapPage(context = context, viewModel = viewModel, camPosState = cameraPositionState)
        }
    }
}
