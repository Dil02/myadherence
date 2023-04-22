package com.example.myadherence.screens.login

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.myadherence.screens.create_account.EmailTextField
import com.example.myadherence.screens.create_account.PasswordTextField

// Login screen composable:
@Composable
fun LoginScreen(
    navController: NavController,
    viewModel: LoginViewModel = hiltViewModel()
){
    val uiState by viewModel.uiState

    Column(
        modifier = Modifier.padding(32.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Spacer(modifier = Modifier.height(120.dp))
        Text(
            text = "Login",
            fontSize = 25.sp,
            fontWeight = FontWeight.SemiBold,
            fontStyle = FontStyle.Italic
        )
        Spacer(modifier = Modifier.height(10.dp))

        // Makes use of the composable functions written in the 'CreateAccountScreen.kt' file:
        EmailTextField(uiState.email, viewModel::onEmailChange)
        PasswordTextField(uiState.password, viewModel::onPasswordChange, "Password")

        // Login button:
        Button( modifier = Modifier.align(Alignment.CenterHorizontally),
            onClick = { viewModel.login(navController)}) {
            Text(
                text = "Login",
                fontSize = 16.sp
            )
        }

        if(!uiState.errorMessage.equals("")) {
            Text(
                text = uiState.errorMessage,
                fontSize = 18.sp
            )
        }
    }
}