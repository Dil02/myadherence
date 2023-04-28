package com.example.myadherence.screens.dose

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.example.myadherence.DOSE_SCREEN
import com.example.myadherence.LOGIN_SCREEN
import com.example.myadherence.MEDICATION_DOSES_SCREEN
import com.example.myadherence.model.Dose
import com.example.myadherence.model.Medicine
import com.example.myadherence.model.service.StorageService
import com.example.myadherence.screens.MyAdherenceViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DoseScreenViewModel @Inject constructor(
    private val storageService: StorageService
) : MyAdherenceViewModel() {

    /* Declares a mutable state of the Dose type.
    This is observed by the DoseScreen composable function in 'DoseScreen.kt'. */
    var dose = mutableStateOf(Dose())
        private set

    // Declares a mutable state.
    var errorMessage = mutableStateOf(String())
        private set

    // This function is used to inform the storage service to fetch a single dose document from Cloud Firestore.
    fun initialise(medicationID: String, doseID: String) {
        viewModelScope.launch {
            storageService.getDose(medicationID, doseID) {
                dose.value = it
            }
        }
    }

    // This function updates the 'status' property of the dose state as the user enters a value.
    fun onStatusChange(newValue: String) {
        dose.value = dose.value.copy(status = newValue)
    }

    // This function updates the 'sideEffects' property of the dose state as the user enters a value.
    fun onSideEffectsChange(newValue: String) {
        dose.value = dose.value.copy(sideEffects = newValue)
    }

    // This function updates the 'timestamp' property of the dose state as the user enters a value.
    fun onTimestampChange(newValue: String) {
        dose.value = dose.value.copy(timestamp = newValue)
    }

    // This function updates the 'skippedReason' property of the dose state as the user enters a value.
    fun onSkippedReasonChange(newValue: String) {
        dose.value = dose.value.copy(skippedReason = newValue)
    }

    // This function instructs the storage service to save the changes made to a Dose object to Cloud Firestore.
    fun updateDose(medicationID: String) {
        if(!validateFields()) return
        storageService.updateDose(medicationID,
            Dose(
                id = dose.value.id,
                status = dose.value.status,
                skippedReason = dose.value.skippedReason,
                timestamp = dose.value.timestamp,
                sideEffects = dose.value.sideEffects
            )
        )
    }

    // This function instructs the storage service to delete a Dose object from Cloud Firestore.
    fun deleteDose(navController: NavController,medicationID: String,medicationName: String)
    {
        storageService.deleteDose(medicationID,dose.value.id)
        navController.navigate(route = "$MEDICATION_DOSES_SCREEN/$medicationID/$medicationName")
    }

    // This function checks to see if the values entered by the user are valid.
    private fun validateFields(): Boolean {
        if(!dose.value.status.equals("Taken") && !dose.value.status.equals("Skipped")) {
            errorMessage.value = "Dose status must be either 'Taken' or 'Skipped'."
            return false
        }
        else if(dose.value.timestamp.equals("")) {
            errorMessage.value = "Timestamp must not be blank."
            return false
        }
        else if(dose.value.status.equals("Taken") && !dose.value.skippedReason.equals("N/A")) {
            errorMessage.value = "Dose status is 'Taken', skipped reason must be 'N/A'."
            return false
        }
        else if(dose.value.status.equals("Skipped") && dose.value.skippedReason.equals("")) {
            errorMessage.value = "Dose status is 'Skipped', skipped reason must not be blank."
            return false
        }
        errorMessage.value = ""
        return true
    }
}