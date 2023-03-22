package com.example.myadherence.screens.medication

import androidx.navigation.NavController
import com.example.myadherence.model.service.StorageService
import com.example.myadherence.screens.MyAdherenceViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MedicationViewModel @Inject constructor(
    private val storageService: StorageService
) : MyAdherenceViewModel() {

}