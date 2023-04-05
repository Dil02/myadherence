package com.example.myadherence.screens.medication_doses

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.myadherence.screens.medication.MedicineNumberField
import com.example.myadherence.screens.medication.MedicineTextField

@Composable
fun MedicationDosesScreen(
    navController: NavController,
    medicationID: String?,
    medicationName: String?,
    viewModel: MedicationDosesScreenViewModel = hiltViewModel()
)
{
    val doses = viewModel.doses

    // Calls the initialise function when composition is started.
    LaunchedEffect(Unit) {
        if (medicationID != null) {
            viewModel.initialise(medicationID)
        }
    }

    Column(
        modifier = Modifier.padding(32.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {

        Text(
            text = medicationName!!,
            fontSize = 18.sp
        )

        for(dose in doses) {
            Button(onClick = { viewModel.goToDose(navController,medicationID!!,medicationName,dose.id)}) {
                Text(
                    text = dose.sideEffects,
                    fontSize = 18.sp
                )
            }
        }
    }
}