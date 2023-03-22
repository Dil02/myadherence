package com.example.myadherence.screens.home

import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.example.myadherence.WELCOME_SCREEN
import com.example.myadherence.model.Medicine
import com.example.myadherence.model.service.AccountService
import com.example.myadherence.model.service.StorageService
import com.example.myadherence.screens.MyAdherenceViewModel
import com.example.myadherence.screens.create_account.CreateAccountUiState
import com.example.myadherence.screens.login.LoginUiState
import com.google.firebase.auth.UserInfo
import com.google.firebase.firestore.auth.User
import com.google.firestore.v1.DocumentDelete
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.coroutines.coroutineContext

// Defines the Home screen ViewModel:
@HiltViewModel
class HomeViewModel @Inject constructor(
    private val accountService: AccountService,
    private val storageService: StorageService
) : MyAdherenceViewModel()
{
    var medicines = mutableStateMapOf<String, Medicine>()
        private set

    fun addListener() {
        viewModelScope.launch { storageService.addListener(accountService.getUserID(),:: onDocumentEvent) }
    }

    fun removeListener() {
        viewModelScope.launch { storageService.removeListener() }
    }

    // This function navigates to the Welcome screen and then signs the user out.
    fun signOut(navController: NavController) {
        navController.navigate(route = WELCOME_SCREEN)
        accountService.signOut()

    }

    fun goToLeaderboard(navController: NavController) {
        navController.navigate(route = "Leaderboard")
    }

    private fun onDocumentEvent(medicine: Medicine) {
        medicines[medicine.id] = medicine
    }

    fun viewMedication(navController: NavController) {
        navController.navigate(route = "Medication")
    }

}
