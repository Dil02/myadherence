package com.example.myadherence.screens.welcome

import android.graphics.Paint.Style
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController


// Welcome screen composable:
@Composable
fun WelcomeScreen(
    navController: NavController,
    viewModel : WelcomeViewModel = hiltViewModel()) // Constructor injection.
{
    Column(
        modifier = Modifier.fillMaxSize(),
        //verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "MyAdherence",
            fontSize = 31.sp,
            fontWeight = FontWeight.Bold,
            fontStyle = FontStyle.Italic,
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(vertical = 100.dp)
        )

        //Image(painter = painterResource(R.drawable.pillsbottle), contentDescription = null)

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
