package com.example.myadherence.screens.dose

import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController

@Composable
fun DoseScreen(
    navController: NavController,
    medicationID: String?,
    medicationName: String?,
    doseID: String?,
    viewModel: DoseScreenViewModel = hiltViewModel()
){
    val dose = viewModel.dose

    // Calls the initialise function when composition is started.
    LaunchedEffect(Unit) {
        if (medicationID != null && doseID !=null) {
            viewModel.initialise(medicationID,doseID)
        }
    }

    Column(
        modifier = Modifier.padding(32.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(
            text = medicationName!! + " Dose",
            fontSize = 24.sp
        )

        DoseScreenTextField(value = dose.value.status, onValueChange = viewModel :: onStatusChange , label = "Status" )
        DoseScreenTextField(value = dose.value.sideEffects, onValueChange = viewModel :: onSideEffectsChange , label = "Experienced Side Effects" )
        DoseScreenTextField(value = dose.value.timestamp, onValueChange = viewModel :: onTimestampChange , label = "Timestamp" )
        DoseScreenTextField(value = dose.value.skippedReason, onValueChange = viewModel :: onSkippedReasonChange , label = "Skipped Reason" )

        Row(
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Button(onClick = { viewModel.deleteDose(navController,medicationID!!,medicationName!!) }) {
                Text(
                    text= "Delete Dose",
                    fontSize = 18.sp
                )
            }
            Button(onClick = {viewModel.updateDose(medicationID!!)}) {
                Text(
                    text = "Save",
                    fontSize = 18.sp
                )
            }
        }

    }
}

@Composable
fun DoseScreenTextField(value: String, onValueChange: (String) -> Unit, label: String)
{
    TextField(
        value= value,
        onValueChange = {onValueChange(it)},
        label = { Text(label)},
        modifier = Modifier.fillMaxWidth()
    )
}