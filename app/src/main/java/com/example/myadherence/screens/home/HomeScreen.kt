package com.example.myadherence.screens.home

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.myadherence.ui.theme.MyAdherenceTheme

@Composable
fun HomeScreen(
    navController: NavController,
    viewModel: HomeViewModel = hiltViewModel()
){

    val medicines = viewModel.medicines

    Column(
        modifier = Modifier.padding(32.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ){

        //adherenceCircle()
        //progressBar()

        Text(
            text = "Hi ",
            fontSize = 16.sp,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )

        Text(
            text = "Adherence Score",
            fontSize = 20.sp,
            fontStyle = FontStyle.Italic
        )

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

        Text(
            text = "Medication Progress",
            fontSize = 20.sp,
            fontStyle = FontStyle.Italic
        )

        for(medicine in medicines.values.toList()) {
            Button(
                onClick = { viewModel.viewMedication(navController) },
                colors = ButtonDefaults.buttonColors(backgroundColor = Color.White),
                border = BorderStroke(0.1.dp,Color.Black)
            ) {
                Text(
                    text= medicine.name,
                    fontSize = 18.sp,
                    color = Color.Black
                )
            }
            Text(
                text = medicine.progress.toString() + "%",
                fontSize = 17.sp
            )
        }

    }

    DisposableEffect(viewModel) {
        viewModel.addListener()
        onDispose { viewModel.removeListener() }
    }
}

@Composable
fun adherenceCircle()
{
    Canvas(modifier = Modifier.fillMaxWidth()) {
        drawCircle(
            androidx.compose.ui.graphics.Color(0xFFD6969D),
            center = Offset(
                170.dp.toPx(),
                55.dp.toPx()
            ),
            radius = 60.dp.toPx()
        )

    }
}

@Composable
fun progressBar()
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
            size = Size(200F,100F),
            cornerRadius = CornerRadius(50F,50F)
        )

    }
}
