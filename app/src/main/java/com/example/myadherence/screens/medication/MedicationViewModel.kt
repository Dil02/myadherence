package com.example.myadherence.screens.medication

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.example.myadherence.model.Medicine
import com.example.myadherence.model.service.StorageService
import com.example.myadherence.screens.MyAdherenceViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MedicationViewModel @Inject constructor(
    private val storageService: StorageService
) : MyAdherenceViewModel() {

    /* Declares a mutable state of the Medicine type.
    This is observed by the MedicationScreen composable function in 'MedicationScreen.kt'. */
    var medication = mutableStateOf(Medicine())
        private set

    // This function is used to inform the storage service to fetch a single medication document from Cloud Firestore.
    fun initialise(medicationID: String) {
        viewModelScope.launch() {
            if(!medicationID.equals("")) {
                storageService.getMedication(medicationID) {
                    medication.value = it
                }
            }
        }
    }

    // This function updates the 'about' property of the medication state as the user enters a value.
    fun onAboutChange(newValue: String){
        medication.value = medication.value.copy(about = newValue)
    }

    // This function updates the 'knownSideEffects' property of the medication state as the user enters a value.
    fun onKnownSideEffectsChange(newValue: String) {
        medication.value = medication.value.copy(knownSideEffects = newValue)
    }

    // This function updates the 'currentPillCount' property of the medication state as the user enters a value.
    fun onCurrentPillCountChange(newValue: String) {
        medication.value = medication.value.copy(currentPillCount = newValue.toIntOrNull() ?: 0)
    }

    // This function updates the 'pillCount' property of the medication state as the user enters a value.
    fun onPillCountChange(newValue: String) {
        medication.value = medication.value.copy(pillCount = newValue.toIntOrNull() ?: 0)
    }

    // This function instructs the storage service to save the changes made to a Medicine object to Cloud Firestore.
    fun updateMedication() {
        storageService.updateMedication(
            Medicine(
                id = medication.value.id,
                name = medication.value.name,
                knownSideEffects = medication.value.knownSideEffects,
                about = medication.value.about,
                pillCount = medication.value.pillCount,
                currentPillCount = medication.value.currentPillCount,
                progress = medication.value.progress
            )
        )
    }

    // This function instructs the storage service to delete a Medicine object from Cloud Firestore.
    fun deleteMedication() {
        // DON'T FORGET TO COMPLETE!!!!!!!!
        println("Delete medication not implemented!")
    }

}