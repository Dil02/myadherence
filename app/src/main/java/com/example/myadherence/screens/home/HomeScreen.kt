package com.example.myadherence.screens.home

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.myadherence.screens.NFCViewModel

@Composable
fun HomeScreen(
    navController: NavController,
    tempViewModel: NFCViewModel,
    viewModel: HomeViewModel = hiltViewModel()
){

    // Declares and initialises the state observed by the composable.
    val medicines = viewModel.medicines

    //DELETE:
    val tempString = tempViewModel.inputText

    // Declares and initialises the state observed by the composable.
    val user = viewModel.user

    Column(
        modifier = Modifier.padding(32.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ){

        Text(
            text = "Hi " + user.value.nickname,
            fontSize = 16.sp,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )

        Text(
            text = "Adherence Score: " + user.value.adherenceScore.toString(),
            fontSize = 20.sp,
            fontStyle = FontStyle.Italic
        )

        //adherenceCircle()
        //Spacer(modifier = Modifier.height(120.dp))

        Button(
            onClick = { viewModel.signOut(navController)},
            modifier = Modifier.align(Alignment.CenterHorizontally)
        ) {
            Text(
                text = "Sign Out",
                fontSize = 18.sp
            )
        }

        Button (
            onClick = { viewModel.goToLeaderboard(navController)},
        ) {
            Text(
                text = "View Leaderboard",
                fontSize = 18.sp
            )
        }

        Text(text=tempString.value, fontSize = 16.sp)

        if(!tempString.value.equals(""))
        {
            Button(onClick = { viewModel.addMedication(tempString.value)}) {
                Text(text = "Add medication", fontSize = 16.sp)
            }
        }

        Text(
            text = "Medication Progress",
            fontSize = 20.sp,
            fontStyle = FontStyle.Italic
        )

        // Creates a vertically scrolling list.
        LazyColumn{
            items(medicines.values.toList()) { medicine ->
                Button(
                    onClick = { viewModel.viewMedication(navController,medicine.id) },
                    colors = ButtonDefaults.buttonColors(backgroundColor = Color.White),
                    border = BorderStroke(0.1.dp,Color.Black)
                ) {
                    Text(
                        text= medicine.name,
                        fontSize = 18.sp,
                        color = Color.Black
                    )
                }
                progressBar(progress = medicine.progress)
                Spacer(modifier = Modifier.height(60.dp))
            }
        }
    }

    DisposableEffect(viewModel) {
        // Calls a function to create a listener when the composable first composes.
        viewModel.addListener()

        /* Called when the composable is no longer needed, for example, changing screen.
           Removing the listener frees up resources which are no longer required.*/
        onDispose { viewModel.removeListener() }
    }

    // Calls 'getUserDetails()' when composition is started.
    LaunchedEffect(Unit) {
        viewModel.getUserDetails()
    }
}

@Composable
fun adherenceCircle()
{
    Canvas(modifier = Modifier.fillMaxWidth()) {
        drawCircle(
            Color(0xFFD6969D),
            center = Offset(
                170.dp.toPx(),
                55.dp.toPx()
            ),
            radius = 60.dp.toPx()
        )
    }
}

@Composable
fun progressBar(progress: Int)
{
    Canvas(modifier = Modifier.fillMaxWidth()) {
        drawRoundRect(
            Color(0xFFD9D9D9),
            topLeft = Offset(50F, 50F),
            size = Size(800F,100F),
            cornerRadius = CornerRadius(50F,50F)
        )
        drawRoundRect(
            Color(0xFFA8CDB7),
            topLeft = Offset(50F, 50F),
            size = Size((progress*10).toFloat(),100F),
            cornerRadius = CornerRadius(50F,50F)
        )

    }
}
