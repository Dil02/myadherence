package com.example.myadherence.screens.home

import android.nfc.Tag
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.example.myadherence.LEADERBOARD_SCREEN
import com.example.myadherence.MEDICATION_SCREEN
import com.example.myadherence.MyAdherenceActivity
import com.example.myadherence.WELCOME_SCREEN
import com.example.myadherence.model.Dose
import com.example.myadherence.model.Medicine
import com.example.myadherence.model.User
import com.example.myadherence.model.service.AccountService
import com.example.myadherence.model.service.StorageService
import com.example.myadherence.screens.MyAdherenceViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject
import kotlin.math.roundToInt

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
        calculateAdherenceScore()
        navController.navigate(route = LEADERBOARD_SCREEN)
    }

    // This function navigates to the Medication screen where the user can view the details of a particular medication.
    fun viewMedication(navController: NavController, medicationID: String) {
        navController.navigate(route = "$MEDICATION_SCREEN/$medicationID")
    }

    // This function adds a Medication to the application based on the contents of the NFC tag which is represented as a string.
    fun addMedication(newString: String)
    {
        val properties = newString.split(",")
        if(properties.size==5)
        {
            val id = ""
            val name = properties.get(0)
            val knownSideEffects = properties.get(1)
            val about = properties.get(2)
            val pillCount = properties.get(3).toInt()
            val dosage = properties.get(4)
            val currentPillCount = pillCount
            val progress = 0
            val points = 0

            storageService.addMedication(Medicine(id,name,knownSideEffects,about,pillCount,currentPillCount,progress,dosage,points))
        }
    }

    /* This function validates the contents of the NFC tag scanned by the user which is represented as a string.
        Correct Format: name,knownSideEffects,about,pillCount,quantity/frequency/times/instructions */
    fun validateMedication(newString: String): Boolean {

        val properties = newString.split(",")

        if(properties.size==5 && properties.get(4).split("/").size==4)
        {
            val dosageProperties = properties.get(4).split("/")
            try {
                properties.get(3).toInt() // Checks to see if the 'pillCount' string can be converted to an Int.
                dosageProperties.get(0).toInt() // quantity
                dosageProperties.get(1).toInt() // frequency
            }
            catch (e: java.lang.NumberFormatException) {
                return false
            }

            return true
        }
        return false
    }

    // This function returns a Medicine object if it exists within the application based on the medication name provided.
    fun doesMedicationExist(medicationName: String): Medicine? {
        for(medicine in medicines)
        {
            if(medicine.value.name.equals(medicationName))
                return medicine.value
        }
        return null
    }

    // This function instructs the storage service to add a 'Taken' Dose object to Cloud Firestore and update a Medication object.
    fun addMedicationDose(medication: Medicine) {
        storageService.addDose(medication.id,
            Dose(
                id = "",
                status = "Taken",
                skippedReason = "N/A",
                timestamp = Calendar.getInstance().time.toString(),
                sideEffects = ""
            )
        )
        // Gets 'quantity' from the dosage property.
        val quantity = medication.dosage.split("/")[0].toInt()
        storageService.updateMedication(
            Medicine(
                id = medication.id,
                name = medication.name,
                knownSideEffects = medication.knownSideEffects,
                about = medication.about,
                pillCount = medication.pillCount,

                // Uses 'quantity' within the dosage property to update the currentPillCount.
                currentPillCount = medication.currentPillCount - quantity,

                /* Calculates the progress by dividing the currentPillCount (minus the quantity just taken)
                    by the medication's overall pillCount multiplied by 100. Both of these values are first converted to the Double
                    type. The result is then rounded to the nearest integer.
                    Finally, this value is subtracted from 100. Note: As the currentPillCount goes down, then progress increases.
                 */
                progress = 100-((medication.currentPillCount - quantity).toDouble()/(medication.pillCount.toDouble())*100).roundToInt(),
                dosage = medication.dosage,
                points = medication.points + 2 // 2 points awarded for 'Taken' Dose.
            )
        )
    }

    /* Declares a mutable state of the User type.
        This is observed by the 'HomeScreen composable function in 'HomeScreen.kt' */
    var user = mutableStateOf(User())
        private set

    // This function is used to inform the account service to fetch a single document relating to the current user.
    fun getUserDetails() {
        accountService.getUserDetails() {
            user.value = it
        }
    }

    /* This function calculates the user's adherence score. This is performed by adding all the points
        of each medication together and then dividing by the number of medications. The result is rounded
        to the nearest integer. */
    private fun calculateAdherenceScore() {
        var totalPoints = 0
        for(medicine in medicines) {
            totalPoints += medicine.value.points
        }
        var adherenceScore = (totalPoints.toDouble()/medicines.values.toList().size.toDouble()).roundToInt()
        accountService.updateAdherenceScore(accountService.getUserID(),adherenceScore)
    }
}
