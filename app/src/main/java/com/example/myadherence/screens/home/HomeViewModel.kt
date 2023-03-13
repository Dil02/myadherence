package com.example.myadherence.screens.home

import androidx.compose.runtime.mutableStateOf
import androidx.navigation.NavController
import com.example.myadherence.WELCOME_SCREEN
import com.example.myadherence.model.service.AccountService
import com.example.myadherence.screens.MyAdherenceViewModel
import com.example.myadherence.screens.create_account.CreateAccountUiState
import com.google.firebase.auth.UserInfo
import com.google.firebase.firestore.auth.User
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

// Defines the Home screen ViewModel:
@HiltViewModel
class HomeViewModel @Inject constructor(
    private val accountService: AccountService
) : MyAdherenceViewModel()
{
    var uiState = mutableStateOf(HomeUiState())
        private set

    //val user = accountService.getUserDetails()

    private val email
        get() = uiState.value.email

    private val nickname
        get() = uiState.value.nickname

    // This function updates the Ui state of the home screen with the current user's details.
    fun getUserInfo() {
        val user = accountService.getUserDetails()
        uiState.value = uiState.value.copy(email = user.email)
        uiState.value = uiState.value.copy(id = user.id)
        uiState.value = uiState.value.copy(nickname = user.nickname)
    }

//    fun getUser() : com.example.myadherence.model.User
//    {
//        return accountService.getUserDetails()
//    }

    // This function navigates to the Welcome screen and then signs the user out.
    fun signOut(navController: NavController) {
        navController.navigate(route = WELCOME_SCREEN)
        accountService.signOut()

    }

}
