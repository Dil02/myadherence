package com.example.myadherence.screens.create_account

import android.widget.NumberPicker.OnValueChangeListener
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModel
import com.example.myadherence.ui.theme.MyAdherenceTheme
import androidx.compose.runtime.*
import androidx.navigation.NavController

// Create Account screen composable:
@Composable
fun CreateAccountScreen(navController: NavController,
    viewModel: CreateAccountViewModel
    //viewModel: CreateAccountViewModel = hiltViewModel()
){
    val uiState by viewModel.uiState

    Column(
        modifier = Modifier.padding(32.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(
            text = "Create Account",
            fontSize = 16.sp,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )

        EmailTextField(uiState.email, viewModel::onEmailChange)
        PasswordTextField(uiState.password, viewModel::onPasswordChange, "Password")
        PasswordTextField(uiState.confirmPassword, viewModel::onConfirmedPasswordChange, "Confirm Password")
        NicknameTextField(uiState.nickname, viewModel::onNicknameChange)

        // Register button:
        Button(onClick = { viewModel.registerAccount(navController)}) {
            Text(
                text = "Register",
                fontSize = 16.sp
            )
        }
    }
}

// Defines an email text field.
@Composable
fun EmailTextField(value: String, onValueChange: (String) -> Unit)
{
    TextField(
        value= value,
        onValueChange = {onValueChange(it)},
        singleLine = true,
        label = { Text("Email Address")}
    )
}

// Defines a password text field.
@Composable
fun PasswordTextField(value: String, onValueChange: (String) -> Unit, label: String)
{
    TextField(
        value= value,
        onValueChange = {onValueChange(it)},
        singleLine = true,
        label = { Text(label)}
    )
}

// Defines a nickname text field.
@Composable
fun NicknameTextField(value: String, onValueChange: (String) -> Unit)
{
    TextField(
        value= value,
        onValueChange = {onValueChange(it)},
        singleLine = true,
        label = { Text("Nickname")}
    )
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    MyAdherenceTheme {
        //CreateAccountScreen()
    }
}
