package com.pdm.weatherapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import android.Manifest
import androidx.activity.viewModels
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.pdm.weatherapp.db.FirebaseDB
import com.pdm.weatherapp.model.FavoriteCity
import com.pdm.weatherapp.ui.theme.WeatherAppTheme

class HomeActivity() : ComponentActivity() {
    private lateinit var fbAuthList: FBAuthListener
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.fbAuthList = FBAuthListener(this)
        val viewModel : MainViewModel by viewModels()

        setContent {
            val showDialog = remember { mutableStateOf(false) }
            val navController = rememberNavController()
            val context = LocalContext.current
            val currentRoute = navController.currentBackStackEntryAsState()
            val showButton = currentRoute.value?.destination?.route !=
                    BottomNavItem.MapPage.route
            val launcher = rememberLauncherForActivityResult(contract =
            ActivityResultContracts.RequestPermission(), onResult = {} )
            WeatherAppTheme {
                if (showDialog.value) FavCityDialog(
                    onDismiss = { showDialog.value = false },
                    onConfirm = { city ->
                        if (city.isNotBlank()){
                            FirebaseDB.add(FavoriteCity())
                        }
                        showDialog.value = false
                    })
                Scaffold(
                    topBar = {
                        TopAppBar(
                            title = { Text("Bem-vindo/a ${viewModel.user.name}") },
                            actions = {
                                IconButton( onClick = { } ) {
                                    Icon(
                                        imageVector = Icons.Filled.ExitToApp,
                                        contentDescription = "Localized description"
                                    )
                                }
                            }
                        )
                    },
                    bottomBar = {
                        BottomNavBar(navController = navController)
                    },
                    floatingActionButton = {
                        if (showButton) {
                            FloatingActionButton(onClick = { showDialog.value = true }) {
                                Icon(Icons.Default.Add, contentDescription = "Adicionar")
                            }
                        }
                    }
                )

                {innerPadding ->
                    Box(modifier = Modifier.padding(innerPadding)) {
                        launcher.launch(Manifest.permission.ACCESS_FINE_LOCATION)


                    MainNavHost(navController = navController, viewModel = viewModel, context = context)
                }

                }
            }
        }
    }
    override fun onStart() {
        super.onStart()
        Firebase.auth.addAuthStateListener(fbAuthList)
    }
    override fun onStop() {
        super.onStop()
        Firebase.auth.removeAuthStateListener(fbAuthList)
    }
}

