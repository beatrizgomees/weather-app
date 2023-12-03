package com.pdm.weatherapp.ui.pages

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.rounded.FavoriteBorder
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.foundation.lazy.items
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import pdm.weatherapp.db.FirebaseDB
import pdm.weatherapp.model.FavoriteCity
import com.pdm.weatherapp.viewmodels.MainViewModel

@Composable
fun ListPage(
    modifier: Modifier = Modifier,
    viewModel: MainViewModel,
    context: Context
) {
    val cityList: List<FavoriteCity> = viewModel.cities

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp)
    ) {
        items(cityList) { c ->
            FavoriteCityItem(favCity = c, onClose = {
               FirebaseDB.remove(c)
            }, onClick = {city ->
                Toast.makeText(context, "Cidade ${city.name} adicionada", Toast.LENGTH_LONG).show()
            })
        }
    }

}


@Composable
fun FavoriteCityItem(
    favCity: FavoriteCity,
    onClick: (FavoriteCity) -> Unit,
    onClose: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.fillMaxWidth().padding(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            Icons.Rounded.FavoriteBorder,
            contentDescription = ""
        )
        Spacer(modifier = Modifier.size(12.dp))
        Column(modifier = modifier.weight(1f)) {
            favCity.name?.let {
                Text(modifier = Modifier,
                    text = it,
                    fontSize = 24.sp)
            }
            favCity.weather?.let {
                Text(modifier = Modifier,
                    text = it,
                    fontSize = 16.sp)
            }
        }
        IconButton(onClick = onClose) {
            Icon(Icons.Filled.Close, contentDescription = "Close")
        }
    }
}