package com.example.myadherence.screens.dose

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.viewModelScope
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

    // This function updates the 'scheduledTime' property of the dose state as the user enters a value.
    fun onScheduledTime(newValue: String) {
        dose.value = dose.value.copy(scheduledTime = newValue)
    }

    // This function instructs the storage service to save the changes made to a Dose object to Cloud Firestore.
    fun updateDose(medicationID: String) {
        storageService.updateDose(medicationID,
            Dose(
                id = dose.value.id,
                status = dose.value.status,
                scheduledTime = dose.value.scheduledTime,
                timestamp = dose.value.timestamp,
                sideEffects = dose.value.sideEffects
            )
        )
    }

    // This function instructs the storage service to delete a Dose object from Cloud Firestore.
    fun deleteDose(medicationID: String)
    {
        storageService.deleteDose(medicationID,dose.value.id)
    }
}