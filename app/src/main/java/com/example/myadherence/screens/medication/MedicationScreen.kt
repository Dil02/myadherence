package com.example.myadherence.screens.medication

import android.widget.NumberPicker.OnValueChangeListener
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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
        Text(text = medication.value.name, fontSize = 26.sp)

        MedicineTextField(value = medication.value.about, onValueChange = viewModel::onAboutChange, label = "About")
        MedicineTextField(value = medication.value.knownSideEffects, onValueChange = viewModel::onKnownSideEffectsChange, label = "Known Side Effects" )
        MedicineNumberField(value = medication.value.currentPillCount.toString(), onValueChange = viewModel::onCurrentPillCountChange, label = "Current Pill Count")
        MedicineNumberField(value = medication.value.pillCount.toString(), onValueChange = viewModel::onPillCountChange, label = "Pill Count")

        Text(text = "Progress: " + medication.value.progress.toString() + "%", fontSize = 18.sp)

        Row(
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Button(onClick = {viewModel.deleteMedication()}) {
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
        }

        Button(onClick = { /*TODO*/ }) {
            Text(
                text = "View Doses",
                fontSize = 20.sp
            )
        }
    }
}

@Composable
fun MedicineTextField(value: String, onValueChange: (String) -> Unit, label: String)
{
    TextField(
        value= value,
        onValueChange = {onValueChange(it)},
        label = { Text(label)},
        modifier = Modifier.fillMaxWidth()
    )
}

@Composable
fun MedicineNumberField(value: String, onValueChange: (String) -> Unit, label: String)
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
