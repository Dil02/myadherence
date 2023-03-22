package com.example.myadherence.screens.medication

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController

@Composable
fun MedicationScreen(
    navController: NavController,
    viewModel: MedicationViewModel = hiltViewModel()
){
    Column(
        modifier = Modifier.padding(32.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(text = "Medicine Name", fontSize = 26.sp)
        
        Spacer(modifier = Modifier.padding(32.dp))
        
        Text(text = "Known side effects", fontSize = 18.sp)
        Text(text = "Current pill count", fontSize = 18.sp)
        Text(text = "Pill count", fontSize = 18.sp)
        Text(text = "Progress %", fontSize = 18.sp)

        Spacer(modifier = Modifier.padding(32.dp))

        Button(onClick = { /*TODO*/ }) {
            Text(
                text = "View Doses",
                fontSize = 20.sp
            )
        }
    }
}