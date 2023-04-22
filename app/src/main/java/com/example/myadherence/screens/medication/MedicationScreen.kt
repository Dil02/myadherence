package com.example.myadherence.screens.medication

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController

@Composable
fun MedicationScreen(
    navController: NavController,
    medicationID: String?,
    viewModel: MedicationViewModel = hiltViewModel()
){
    // Declares and initialises the state observed by the composable.
    val medication = viewModel.medication

    // Declares and initialises another state observed by the composable.
    val errorMessage = viewModel.errorMessage

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
        Text(text = medication.value.name, fontSize = 21.sp,
            fontStyle = FontStyle.Italic,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )

        GeneralTextField(value = medication.value.about, onValueChange = viewModel::onAboutChange, label = "About")
        GeneralTextField(value = medication.value.knownSideEffects, onValueChange = viewModel::onKnownSideEffectsChange, label = "Known Side Effects" )
        GeneralNumberField(value = medication.value.currentPillCount.toString(), onValueChange = viewModel::onCurrentPillCountChange, label = "Current Pill Count")
        GeneralNumberField(value = medication.value.pillCount.toString(), onValueChange = viewModel::onPillCountChange, label = "Pill Count")
        GeneralTextField(value = medication.value.dosage, onValueChange = viewModel::onDosageChange, label = "Dosage: Quantity/Frequency/Times/Instructions")

        Text(text = "Progress: " + medication.value.progress.toString() + "%", fontSize = 18.sp)

        Row(
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Button(onClick = {viewModel.deleteMedication(navController)}) {
                Text(
                    text = "Delete",
                    fontSize = 16.sp
                )
            }

            Button(onClick = {viewModel.updateMedication()}) {
                Text(
                    text = "Save",
                    fontSize = 16.sp
                )
            }

            Button(onClick = { viewModel.goToMedicationDoses(navController, medication.value.id,medication.value.name) }) {
                Text(
                    text = "Doses",
                    fontSize = 16.sp
                )
            }
        }

        var skippedReason by remember {mutableStateOf("")}
        GeneralTextField(value = skippedReason , onValueChange = {skippedReason = it} , label = "Provide reason for skipping dose" )

        if(skippedReason.length > 3)
        {
            Button(onClick = {viewModel.addSkippedMedicationDose(navController,medication.value,skippedReason)}) {
                Text(
                    text = "Record skipped dose",
                    fontSize = 18.sp
                )
            }
        }

        if(!errorMessage.value.equals("")) {
            Text(text = errorMessage.value, fontSize = 18.sp)
        }
    }
}

@Composable
fun GeneralTextField(value: String, onValueChange: (String) -> Unit, label: String)
{
    TextField(
        value= value,
        onValueChange = {onValueChange(it)},
        label = { Text(label)},
        modifier = Modifier.fillMaxWidth()
    )
}

@Composable
fun GeneralNumberField(value: String, onValueChange: (String) -> Unit, label: String)
{
    TextField(
        value = value,
        onValueChange = {onValueChange(it)},
        label = { Text(label)},
        modifier = Modifier.fillMaxWidth(),
        singleLine = true,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
    )
}
