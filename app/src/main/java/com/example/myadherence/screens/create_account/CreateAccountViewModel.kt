package com.example.myadherence.screens.create_account

import androidx.compose.runtime.mutableStateOf
import androidx.navigation.NavController
import com.example.myadherence.HOME_SCREEN
import com.example.myadherence.screens.MyAdherenceViewModel
import com.example.myadherence.model.service.AccountService
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

// Defines the Create Account screen ViewModel:
@HiltViewModel
class CreateAccountViewModel @Inject constructor(
    private val accountService: AccountService,
) : MyAdherenceViewModel()
{

    var uiState = mutableStateOf(CreateAccountUiState())
        private set

    private val email
        get() = uiState.value.email

    private val password
        get() = uiState.value.password

    private val nickname
        get() = uiState.value.nickname

    // This function updates the 'email' state as the user enters a value.
    fun onEmailChange(newValue: String){
        uiState.value = uiState.value.copy(email = newValue)
    }

    // This function updates the 'password' state as the user enters a value.
    fun onPasswordChange(newValue: String){
        uiState.value = uiState.value.copy(password = newValue)
    }

    // This function updates the 'confirmPassword' state as the user enters a value.
    fun onConfirmedPasswordChange(newValue: String){
        uiState.value = uiState.value.copy(confirmPassword = newValue)
    }

    // This function updates the 'nickname' state as the user enters a value.
    fun onNicknameChange(newValue: String){
        uiState.value = uiState.value.copy(nickname = newValue)
    }

    // This function creates an account for the user using the email and password they have provided and navigates to the home screen.
    fun registerAccount(navController: NavController){
        accountService.register(email,password,nickname) { error ->
            if(error==null)
            {
                println("registered successfully")
                navController.navigate(route = HOME_SCREEN)
            }
            else
            {
                println("The error is ' ${error.message}'")
            }
        }
    }

}