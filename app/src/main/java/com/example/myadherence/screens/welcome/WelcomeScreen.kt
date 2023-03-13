package com.example.myadherence.screens.welcome

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

// Welcome screen composable:
@Composable
fun WelcomeScreen(navController: NavController, viewModel : WelcomeViewModel)

{
    Column(
        modifier = Modifier.padding(32.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(
            text = "MyAdherence",
            fontSize = 28.sp,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )

        // Login button:
        Button(
            onClick = { viewModel.loginScreen(navController)},
            modifier = Modifier.align(Alignment.CenterHorizontally))
        {
            Text(
                text = "Login",
                fontSize = 16.sp
            )
        }

        // Register button:
        Button(
            onClick = { viewModel.createAccountScreen(navController)},
            modifier = Modifier.align(Alignment.CenterHorizontally))
        {
            Text(
                text = "Register",
                fontSize = 16.sp
            )
        }
    }
}
