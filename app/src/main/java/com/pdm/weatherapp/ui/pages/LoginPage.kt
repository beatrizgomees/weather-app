package com.pdm.weatherapp.ui.pages

import android.app.Activity
import android.content.Intent
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.firebase.auth.auth
import com.pdm.weatherapp.ui.activities.MainActivity
import com.pdm.weatherapp.ui.activities.RegisterActivity


@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true)
@Composable
fun LoginPage(modifier: Modifier = Modifier){
    var email by rememberSaveable { mutableStateOf("")}
    var senha by rememberSaveable { mutableStateOf("") }
    val activity = LocalContext.current as? Activity

    Column (
        modifier = Modifier.padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,

    ) {
        Text(text = "Bem-Vindo/a!", fontSize = 24.sp)
        Spacer(modifier = Modifier.size(24.dp))
        OutlinedTextField(

            value = email,
            label = { Text(text = "Digite seu e-mail") },
            modifier = Modifier.fillMaxWidth(),
            onValueChange = { email = it }
        )
        Spacer(modifier = Modifier.size(24.dp))
        OutlinedTextField(
            value = senha,
            label = { Text(text = "Digite sua senha") },
            modifier = Modifier.fillMaxWidth(),
            onValueChange = { senha = it },
            visualTransformation = PasswordVisualTransformation()
        )
        Spacer(modifier = Modifier.size(24.dp))

        Row(
            modifier = modifier
        ) {
            Button(
                onClick = {
                    com.google.firebase.Firebase.auth.signInWithEmailAndPassword(email, senha)
                        .addOnCompleteListener(activity!!) { task ->
                            if (task.isSuccessful) {
                                activity.startActivity(
                                    Intent(activity, MainActivity::class.java).setFlags(
                                        Intent.FLAG_ACTIVITY_SINGLE_TOP
                                    )
                                )
                                Toast.makeText(activity, "Login OK!", Toast.LENGTH_LONG).show()
                            } else {
                                Toast.makeText(activity, "Login FALHOU!", Toast.LENGTH_LONG).show()
                            }
                        }
                },
                enabled = email.isNotEmpty() && senha.isNotEmpty()
            ) {
                Text("Login")
            }
            Spacer(modifier = Modifier.width(16.dp))

            Button(
                onClick = { email = ""; senha = "" }
            ) {
                Text("Limpar")
            }

            Spacer(modifier = Modifier.width(16.dp))

            Button(
                onClick = {
                    activity?.startActivity(
                        Intent(activity, RegisterActivity::class.java).setFlags(
                            Intent.FLAG_ACTIVITY_SINGLE_TOP
                        )
                    )
                }
            ) {
                Text("Cadastrar")
            }
        }
    }
}