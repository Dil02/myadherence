package com.example.myadherence.screens.welcome

import androidx.navigation.NavController
import com.example.myadherence.CREATE_ACCOUNT_SCREEN
import com.example.myadherence.HOME_SCREEN
import com.example.myadherence.LOGIN_SCREEN
import com.example.myadherence.model.service.AccountService
import com.example.myadherence.screens.MyAdherenceViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

// Defines the Welcome Screen ViewModel:
@HiltViewModel
class WelcomeViewModel @Inject constructor(
    private val accountService: AccountService // Injecting 'AccountService' as a dependency.
) : MyAdherenceViewModel() {

    // This function is used to navigate to the login screen.
    fun loginScreen(navController: NavController)
    {
        navController.navigate(route = LOGIN_SCREEN)
    }

    // This function is used to navigate to the create account screen.
    fun createAccountScreen(navController: NavController)
    {
        navController.navigate(route = CREATE_ACCOUNT_SCREEN)
    }

    // This function returns a Boolean value depending on whether or not a user is currently logged in.
    fun loggedIn() : Boolean
    {
        return accountService.loggedIn()
    }
}