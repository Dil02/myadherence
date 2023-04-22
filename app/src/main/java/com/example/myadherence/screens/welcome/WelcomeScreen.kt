package com.example.myadherence.screens.welcome


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
import com.example.myadherence.R


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
        Spacer(modifier = Modifier.height(100.dp))
        Text(
            text = "MyAdherence",
            fontSize = 31.sp,
            fontWeight = FontWeight.SemiBold,
            fontStyle = FontStyle.Italic,
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
        )

        Spacer(modifier = Modifier.height(10.dp))
        Image(painter = painterResource(R.drawable.pillsbottle), contentDescription = null)

        /* Image source:
        <a href="https://www.flaticon.com/free-icons/pills" title="pills icons">Pills icons created by ghost_icon - Flaticon</a>
        */

        Spacer(modifier = Modifier.height(30.dp))

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

        Spacer(modifier = Modifier.height(20.dp))

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
