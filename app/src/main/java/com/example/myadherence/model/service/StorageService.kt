package com.example.myadherence.model.service

import com.example.myadherence.model.Medicine
import com.example.myadherence.model.User

interface StorageService {
    fun addListener(userID: String,onDocumentEvent: (Medicine) -> Unit)
    fun removeListener()
    fun getMedication(medicationID: String, onSuccess: (Medicine) -> Unit)
    fun updateMedication(medication: Medicine)
}