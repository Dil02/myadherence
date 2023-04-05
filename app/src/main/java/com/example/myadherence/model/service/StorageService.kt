package com.example.myadherence.model.service

import com.example.myadherence.model.Dose
import com.example.myadherence.model.Medicine
import com.example.myadherence.model.User

interface StorageService {

    // Medication functions:
    fun addListener(userID: String,onDocumentEvent: (Medicine) -> Unit)
    fun removeListener()
    fun getMedication(medicationID: String, onSuccess: (Medicine) -> Unit)
    fun updateMedication(medication: Medicine)
    fun addMedication(medication: Medicine)
    fun deleteMedication(medicationID: String)

    // Dose functions
    fun getDoses(medicationID: String, onSuccess: (ArrayList<Dose>) -> Unit)
    fun getDose(medicationID: String, doseID: String, onSuccess: (Dose) -> Unit)
    fun updateDose(medicationID: String, dose: Dose)
    fun deleteDose(medicationID: String, doseID: String)
    fun addDose(medicationID: String, dose: Dose)
}