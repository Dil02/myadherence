package com.example.myadherence.screens.medication_doses

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController


@Composable
fun MedicationDosesScreen(
    navController: NavController,
    medicationID: String?,
    medicationName: String?,
    viewModel: MedicationDosesScreenViewModel = hiltViewModel()
)
{
    val doses = viewModel.doses

    Column(
        modifier = Modifier.padding(32.dp).fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {

        Text(
            text = medicationName!! + " Doses",
            modifier = Modifier.align(Alignment.CenterHorizontally),
            fontSize = 21.sp,
            fontStyle = FontStyle.Italic,
            fontWeight = FontWeight.SemiBold
        )

        Spacer(modifier = Modifier.height(3.dp))

        if(doses.isEmpty()) {
            Text(text = "No doses recorded.", fontSize = 18.sp)
        }

        if(doses.isNotEmpty()) {
            Text(text = "Taken Doses", fontSize = 18.sp, fontStyle = FontStyle.Italic)
        }

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

                    Spacer(modifier = Modifier.height(5.dp))
                }
            }
        }

        if(doses.isNotEmpty()) {
            Text(text = "Skipped Doses", fontSize = 18.sp, fontStyle = FontStyle.Italic)
        }

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

                    Spacer(modifier = Modifier.height(5.dp))
                }
            }
        }
    }

    // Calls the initialise function when composition is started.
    LaunchedEffect(Unit) {
        if (medicationID != null) {
            viewModel.initialise(medicationID)
        }
    }

}