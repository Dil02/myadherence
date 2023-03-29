package com.example.myadherence.screens.home

import androidx.compose.runtime.mutableStateMapOf
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.example.myadherence.LEADERBOARD_SCREEN
import com.example.myadherence.MEDICATION_SCREEN
import com.example.myadherence.WELCOME_SCREEN
import com.example.myadherence.model.Medicine
import com.example.myadherence.model.service.AccountService
import com.example.myadherence.model.service.StorageService
import com.example.myadherence.screens.MyAdherenceViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

// Defines the Home screen ViewModel:
@HiltViewModel
class HomeViewModel @Inject constructor(
    private val accountService: AccountService,
    private val storageService: StorageService
) : MyAdherenceViewModel()
{
    /* The code below was sampled from: https://github.com/FirebaseExtended/make-it-so-android/blob/v1.0.0/app/src/main/java/com/example/makeitso/screens/tasks/TasksViewModel.kt,
        indicated by '*****************************************'.
        It is used to declare a mutable state, launch and remove cloud firestore listeners and update the mutable state.
     */

    //*****************************************
    /* Declares a map (mutable state type) of the Medicine type indexed using a String value.
    This is observed by the HomeScreen composable function in 'HomeScreen.kt'. */
    var medicines = mutableStateMapOf<String, Medicine>()
        private set

    // This function informs the storage service to add a listener.
    fun addListener() {
        viewModelScope.launch { storageService.addListener(accountService.getUserID(),:: onDocumentEvent) }
    }

    // This function informs the storage service to remove a listener.
    fun removeListener() {
        viewModelScope.launch { storageService.removeListener() }
    }

    // This function updates the 'medicines' map using the object of Medicine type being passed.
    private fun onDocumentEvent(medicine: Medicine) {
        medicines[medicine.id] = medicine
    }

    //*****************************************




    // This function navigates to the Welcome screen and then signs the user out.
    fun signOut(navController: NavController) {
        navController.navigate(route = WELCOME_SCREEN)
        accountService.signOut()

    }

    // This function navigates to the Leaderboard screen.
    fun goToLeaderboard(navController: NavController) {
        navController.navigate(route = LEADERBOARD_SCREEN)
    }

    // This function navigates to the Medication screen where the user can view the details of a particular medication.
    fun viewMedication(navController: NavController, medicationID: String) {
        navController.navigate(route = "$MEDICATION_SCREEN/$medicationID")
    }

}
