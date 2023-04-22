package com.example.myadherence.screens.create_account

import android.annotation.SuppressLint
import android.widget.NumberPicker.OnValueChangeListener
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.ExtendedFloatingActionButton
import androidx.compose.material.Scaffold
import androidx.compose.material.ScaffoldState
import androidx.compose.material.Snackbar
import androidx.compose.material.SnackbarDuration
import androidx.compose.material.SnackbarResult
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.rememberScaffoldState
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
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.navigation.NavController
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

// Create Account screen composable:
@Composable
fun CreateAccountScreen(
    navController: NavController,
    viewModel: CreateAccountViewModel = hiltViewModel()
){
    val uiState by viewModel.uiState

    Column(
        modifier = Modifier.padding(32.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Spacer(modifier = Modifier.height(80.dp))
        Text(
            text = "Create Account",
            fontSize = 25.sp,
            fontWeight = FontWeight.SemiBold,
            fontStyle = FontStyle.Italic
        )
        Spacer(modifier = Modifier.height(10.dp))

        EmailTextField(uiState.email, viewModel::onEmailChange)
        PasswordTextField(uiState.password, viewModel::onPasswordChange, "Password")
        PasswordTextField(uiState.confirmPassword, viewModel::onConfirmedPasswordChange, "Confirm Password")
        NicknameTextField(uiState.nickname, viewModel::onNicknameChange)

        // Register button:
        Button(modifier = Modifier.align(Alignment.CenterHorizontally),
            onClick = { viewModel.registerAccount(navController)}) {
            Text(
                text = "Register",
                fontSize = 16.sp
            )
        }

        if(!uiState.errorMessage.equals(""))
        {
            Text(
                text = uiState.errorMessage,
                fontSize = 18.sp
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
        label = { Text("Email Address")},
        modifier = Modifier.fillMaxWidth()
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
        label = { Text(label)},
        modifier = Modifier.fillMaxWidth(),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
        visualTransformation = PasswordVisualTransformation()
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
        label = { Text("Nickname")},
        modifier = Modifier.fillMaxWidth()

    )
}

