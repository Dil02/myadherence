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

    private val confirmPassword
        get() = uiState.value.confirmPassword

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
        if(!validateFields()) return
        accountService.register(email,password,nickname) { error ->
            if(error==null)
            {
                println("registered successfully")
                navController.navigate(route = HOME_SCREEN)
            }
            else
            {
                println("The error is ' ${error.message}'")
                uiState.value = uiState.value.copy(errorMessage = error.message.toString())
            }
        }
    }

    // This function checks to see if the values entered by the user are valid.
    private fun validateFields(): Boolean {
        if(nickname.equals("") || email.equals("") || password.equals("") || confirmPassword.equals("")) {
            uiState.value = uiState.value.copy(errorMessage = "Please ensure fields are not blank.")
            return false
        }
        else if(!password.equals(confirmPassword)) {
            uiState.value = uiState.value.copy(errorMessage = "Ensure passwords match.")
            return false
        }
        else if(password.length<6) {
            uiState.value = uiState.value.copy(errorMessage = "Password must be longer than 6 characters.")
            return false
        }
        return true
    }

}