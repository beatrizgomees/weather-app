package com.pdm.weatherapp

import android.content.Context
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost

import androidx.navigation.compose.currentBackStackEntryAsState
import com.google.maps.android.compose.rememberCameraPositionState

@Composable
fun BottomNavBar(navController: NavHostController) {
    val items = listOf(
        BottomNavItem.HomePage,
        BottomNavItem.ListPage,
        BottomNavItem.MapPage,
    )
    NavigationBar(
    contentColor = Color.Black
    ){
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route

        items.forEach{
            item -> NavigationBarItem(selected = currentRoute == item.route, icon = { Icon(imageVector = item.icon, contentDescription = item.title) }, label = { Text(text = item.title, fontSize = 12.sp) }, alwaysShowLabel = true, onClick = {
                navController.navigate(item.route) {
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
fun MainNavHost(navController: NavHostController, viewModel: FavoriteCitiesViewModel, context: Context) {
    val cameraPositionState = rememberCameraPositionState()
    NavHost(navController = navController, startDestination = BottomNavItem.HomePage.route) {
        composable(route = BottomNavItem.HomePage.route) {
            HomePage(viewModel = viewModel, context = context)
        }
        composable(route = BottomNavItem.ListPage.route) {
            ListPage(viewModel = viewModel, context = context)
        }
        composable(route = BottomNavItem.MapPage.route) {
            MapPage(viewModel = viewModel, context = context, camPosState = cameraPositionState)
        }
    }
}

