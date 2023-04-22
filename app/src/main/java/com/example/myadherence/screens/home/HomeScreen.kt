package com.example.myadherence.screens.home

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.ExperimentalTextApi
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.drawText
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.toSize
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
            fontSize = 17.sp,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )
        Spacer(modifier = Modifier.height(10.dp))

        Text(modifier = Modifier
            .align(Alignment.CenterHorizontally)
            .padding(16.dp)
            .drawBehind {
                drawCircle(
                    //Color(0xFFD6969D),
                    Color(214, 150, 157, 55),
                    radius = 55.dp.toPx()
                )
            },
            text = user.value.adherenceScore.toString(),
            fontSize = 35.sp,
            fontStyle = FontStyle.Italic,
            //color = Color.White
        )

        Spacer(modifier = Modifier.height(5.dp))
        Text(
            modifier = Modifier.align(Alignment.CenterHorizontally),
            text = "Adherence Score",
            fontSize = 18.sp,
            fontStyle = FontStyle.Italic,
            fontWeight = FontWeight.SemiBold
        )
        Spacer(modifier = Modifier.height(5.dp))

        Row(
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        )
        {
            Button(
                onClick = { viewModel.signOut(navController)}
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
                    text = "Leaderboard",
                    fontSize = 18.sp
                )
            }
        }

        // Checks to see if the content on the NFC tag is valid and displays buttons accordingly
        // The user cannot add a mediation if it already exists.
        if(!tempString.value.equals("") && viewModel.validateMedication(tempString.value))
        {
            //Text(text=tempString.value, fontSize = 16.sp)
            displayMedicationInfo(text = tempString.value)
            // The variable 'result' will contain either an existing Medication object or null.
            // The user can only record a dose if they have already added the Medicine to the app.
            val result=viewModel.doesMedicationExist(tempString.value.toString().split(",")[0])
            if(result!=null) {
                Button(onClick = { viewModel.addMedicationDose(result)}) {
                    Text(text = "Record dose", fontSize = 16.sp)
                }
            }
            else {
                Button(onClick = { viewModel.addMedication(tempString.value)}) {
                    Text(text = "Add medication", fontSize = 16.sp)
                }
            }
        }
        else
        {
            Text(text = "Invalid NFC tag detected.", fontSize = 17.sp)
        }

        Text(
            text = "Medication Progress",
            fontSize = 18.sp,
            fontStyle = FontStyle.Italic,
            fontWeight = FontWeight.SemiBold
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
                ProgressBar(progress = medicine.progress)
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
fun displayMedicationInfo(text: String) {
    val properties = text.split(",")
    val dosageProperties = properties[4].split("/")
    LazyRow(
        horizontalArrangement =
            Arrangement.spacedBy(10.dp)
    )
    {
        item {Text(text = "Name: ${properties[0]}", fontSize = 17.sp)}
        item {Text(text = "Side Effects: ${properties[1]}", fontSize = 17.sp)}
        item {Text(text = "About: ${properties[2]}", fontSize = 17.sp)}
        item {Text(text = "Pill Count: ${properties[3]}", fontSize = 17.sp)}
        item {Text(text = "Dose quantity: ${dosageProperties[0]}", fontSize = 17.sp)}
        item {Text(text = "Frequency: ${dosageProperties[1]}", fontSize = 17.sp)}
        item {Text(text = "Time: ${dosageProperties[2]}", fontSize = 17.sp)}
        item {Text(text = "Instructions: ${dosageProperties[3]}", fontSize = 17.sp)}
    }
}

@Composable
fun ProgressBar(progress: Int) {
    Canvas(modifier = Modifier.fillMaxWidth()) {
        drawRoundRect(
            Color(0xFFD9D9D9),
            topLeft = Offset(50F, 50F),
            size = Size(800F,100F),
            cornerRadius = CornerRadius(50F,50F)
        )

//        val random = Random.nextInt(1,4)
//
//        var color: Long = if(random == 1) {
//            0xFFA8CDB7 // Green
//        } else if(random == 2) {
//            0xFF97DBDF // Blue
//        } else {
//            0xFFD0B6E5 // Purple
//        }

        drawRoundRect(
            Color(0XFFA8CDB7),
            topLeft = Offset(50F, 50F),
            size = Size((progress*8).toFloat(),100F), // progress is multiplied by 8, due to rectangle width.
            cornerRadius = CornerRadius(50F,50F)
        )
    }
}
