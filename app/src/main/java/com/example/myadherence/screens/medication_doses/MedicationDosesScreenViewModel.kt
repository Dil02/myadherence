package com.example.myadherence.screens.medication_doses

import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.example.myadherence.DOSE_SCREEN
import com.example.myadherence.model.Dose
import com.example.myadherence.model.service.StorageService
import com.example.myadherence.screens.MyAdherenceViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MedicationDosesScreenViewModel @Inject constructor(
    private val storageService: StorageService
) : MyAdherenceViewModel() {

    // Declares an ArrayList of the type Dose.
    var doses = ArrayList<Dose>()
        private set

    /* This function is used to inform the storage service to fetch all the dose documents relating to a specific medication from
        Cloud Firestore. */
    fun initialise(medicationID: String) {
        viewModelScope.launch() {
             storageService.getDoses(medicationID) {
                    doses = it
             }
        }
    }

    // This function navigates to the Dose screen where the user can view the details of a particular dose.
    fun goToDose(navController: NavController, medicationID: String, medicationName: String, doseID: String) {
        navController.navigate(route = "$DOSE_SCREEN/$medicationID/$medicationName/$doseID")
    }
}