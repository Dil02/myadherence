package com.example.myadherence.screens.login

import androidx.compose.runtime.mutableStateOf
import androidx.navigation.NavController
import com.example.myadherence.CREATE_ACCOUNT_SCREEN
import com.example.myadherence.HOME_SCREEN
import com.example.myadherence.LOGIN_SCREEN
import com.example.myadherence.model.service.AccountService
import com.example.myadherence.screens.MyAdherenceViewModel
import com.example.myadherence.screens.home.HomeScreen
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

// Defines the Login Screen ViewModel:
@HiltViewModel
class LoginViewModel @Inject constructor(
    private val accountService: AccountService,
)  : MyAdherenceViewModel()
{
    var uiState = mutableStateOf(LoginUiState())
        private set

    private val email
        get() = uiState.value.email

    private val password
        get() = uiState.value.password

    // This function updates the 'email' state as the user enters a value.
    fun onEmailChange(newValue: String){
        uiState.value = uiState.value.copy(email = newValue)
    }

    // This function updates the 'password' state as the user enters a value.
    fun onPasswordChange(newValue: String){
        uiState.value = uiState.value.copy(password = newValue)
    }

    // This function navigates to the Home Screen if a user is successfully authenticated.
    fun login(navController: NavController){
        accountService.authenticate(email,password) { error ->
            if(error==null)
            {
                println("Logged in successfully")
                navController.navigate(route = HOME_SCREEN) {popUpTo(LOGIN_SCREEN) {inclusive=true} }
            }
            else
            {
                println("The error is ' ${error.message}'")
            }
        }
    }
}