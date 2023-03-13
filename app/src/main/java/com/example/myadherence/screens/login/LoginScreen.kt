package com.example.myadherence.screens.login

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.myadherence.screens.create_account.EmailTextField
import com.example.myadherence.screens.create_account.PasswordTextField

// Login screen composable:
@Composable
fun LoginScreen(navController: NavController,
    viewModel: LoginViewModel
    //viewModel: LoginViewModel = hiltViewModel()
){
    val uiState by viewModel.uiState

    Column(
        modifier = Modifier.padding(32.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(
            text = "Login",
            fontSize = 16.sp,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )

        // Makes use of the composable functions written in the 'CreateAccountScreen.kt' file:
        EmailTextField(uiState.email, viewModel::onEmailChange)
        PasswordTextField(uiState.password, viewModel::onPasswordChange, "Password")

        // Login button:
        Button(onClick = { viewModel.login(navController)}) {
            Text(
                text = "Login",
                fontSize = 16.sp
            )
        }
    }
}