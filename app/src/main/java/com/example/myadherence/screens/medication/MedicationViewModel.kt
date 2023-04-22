package com.example.myadherence.screens.medication

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.example.myadherence.HOME_SCREEN
import com.example.myadherence.MEDICATION_DOSES_SCREEN
import com.example.myadherence.model.Dose
import com.example.myadherence.model.Medicine
import com.example.myadherence.model.service.StorageService
import com.example.myadherence.screens.MyAdherenceViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*
import javax.inject.Inject
import kotlin.math.roundToInt

@HiltViewModel
class MedicationViewModel @Inject constructor(
    private val storageService: StorageService
) : MyAdherenceViewModel() {

    /* Declares a mutable state of the Medicine type.
    This is observed by the MedicationScreen composable function in 'MedicationScreen.kt'. */
    var medication = mutableStateOf(Medicine())
        private set

    // Declares a mutable state.
    var errorMessage = mutableStateOf(String())
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
        updateProgress()
    }

    // This function updates the 'pillCount' property of the medication state as the user enters a value.
    fun onPillCountChange(newValue: String) {
        medication.value = medication.value.copy(pillCount = newValue.toIntOrNull() ?: 0)
        updateProgress()
    }

    // This function updates the 'dosage' property of the medication state as the user enters a value.
    fun onDosageChange(newValue: String) {
        medication.value = medication.value.copy(dosage = newValue)
    }

    // This private function updates the 'progress' property of the medication state.
    private fun updateProgress()
    {
        /* Calculates the progress by dividing the currentPillCount by the medication's overall pillCount
            multiplied by 100. Both of these values are first converted to the Double
            type. The result is then rounded to the nearest integer.
            Finally, this value is subtracted from 100. Note: As the currentPillCount goes down, then progress increases.
         */
        medication.value = medication.value.copy(
            progress = 100-((medication.value.currentPillCount.toDouble()/medication.value.pillCount.toDouble())*100).roundToInt())
    }

    // This function instructs the storage service to save the changes made to a Medicine object to Cloud Firestore.
    fun updateMedication() {
        if(!validateFields()) return
        storageService.updateMedication(
            Medicine(
                id = medication.value.id,
                name = medication.value.name,
                knownSideEffects = medication.value.knownSideEffects,
                about = medication.value.about,
                pillCount = medication.value.pillCount,
                currentPillCount = medication.value.currentPillCount,
                progress = medication.value.progress,
                dosage = medication.value.dosage,
                points = medication.value.points
            )
        )
    }

    // This function instructs the storage service to delete a Medicine object from Cloud Firestore.
    fun deleteMedication(navController: NavController) {
        storageService.deleteMedication(medication.value.id)
        navController.navigate(HOME_SCREEN)
    }

    // This function navigates to the Medication Doses screen where the user can view all the doses of a particular medication.
    fun goToMedicationDoses(navController: NavController, medicationID: String, medicationName: String) {
        navController.navigate(route = "$MEDICATION_DOSES_SCREEN/$medicationID/$medicationName")
    }

    // This function instructs the storage service to add a 'Skipped' Dose object to Cloud Firestore.
    fun addSkippedMedicationDose(navController: NavController,medication: Medicine, skippedReason: String ) {
        storageService.addDose(medication.id,
            Dose(
                id = "",
                status = "Skipped",
                skippedReason = skippedReason,
                timestamp = getCurrentTimestamp(),
                sideEffects = "N/A"
            )
        )
        storageService.updateMedicationPoints(medication.id,medication.points+1) // Skipped dose awarded 1 point.
        navController.navigate(route = "$MEDICATION_DOSES_SCREEN/${medication.id}/${medication.name}")
    }

    // This function is used to get the current date and time.
    private fun getCurrentTimestamp(): String {
//        val calendar = Calendar.getInstance()
//        val year = calendar.get(Calendar.YEAR)
//        val month = calendar.get(Calendar.MONTH) + 1 // Month is zero based.
//        val day = calendar.get(Calendar.DAY_OF_MONTH)
//        val hour = calendar.get(Calendar.HOUR_OF_DAY)
//        val minute = calendar.get(Calendar.MINUTE)
//        val second = calendar.get(Calendar.SECOND)
//        return "$day/$month/$year $hour:$minute:$second"
        return Calendar.getInstance().time.toString()
    }

    // This function checks to see if the values entered by the user are valid.
    private fun validateFields(): Boolean {

        val dosageProperties = medication.value.dosage.split("/")

        if(medication.value.about.equals("") || medication.value.knownSideEffects.equals("")
            || medication.value.dosage.equals("")) {
            errorMessage.value = "Please ensure fields are not blank."
            return false
        }
        else if(medication.value.pillCount< medication.value.currentPillCount) {
            errorMessage.value = "Pill count must be equal to or greater than current pill count."
            return false
        }
        else if(dosageProperties.size!=4) {
            errorMessage.value = "The dosage value is not of the correct length."
            return false
        }
        try {
            val value1 = dosageProperties[0].toInt()
            val value2 = dosageProperties[1].toInt()
            if(value1 <=0 || value2 <=0) throw java.lang.NumberFormatException()

        }
        catch (e: java.lang.NumberFormatException) {
            errorMessage.value = "The quantity and frequency values should be integers greater than 0."
            return false
        }
        errorMessage.value=""
        return true
    }
}