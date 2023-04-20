package com.example.myadherence.screens.medication_doses

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.myadherence.screens.medication.GeneralNumberField
import com.example.myadherence.screens.medication.GeneralTextField

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


        Text(text = "Taken Doses", fontSize = 18.sp, fontStyle = FontStyle.Italic)
        Spacer(modifier = Modifier.height(10.dp))

        // Displays Doses which have been recorded as 'Taken':
        LazyColumn{
            items(doses.toList()) { dose ->
                if(dose.status.equals("Taken"))
                {
                    Button(onClick = { viewModel.goToDose(navController,medicationID!!,medicationName,dose.id)}) {
                        Text(
                            text = dose.timestamp,
                            fontSize = 18.sp
                        )
                    }
                    Spacer(modifier = Modifier.height(10.dp))
                }
            }
        }

        Text(text = "Skipped Doses", fontSize = 18.sp, fontStyle = FontStyle.Italic)
        Spacer(modifier = Modifier.height(10.dp))

        // Displays Doses which have been recorded as 'Skipped':
        LazyColumn{
            items(doses.toList()) { dose ->
                if(dose.status.equals("Skipped"))
                {
                    Button(onClick = { viewModel.goToDose(navController,medicationID!!,medicationName,dose.id)}) {
                        Text(
                            text = dose.timestamp,
                            fontSize = 18.sp
                        )
                    }
                    Spacer(modifier = Modifier.height(10.dp))
                }
            }
        }

    }
}