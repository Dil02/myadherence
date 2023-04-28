package com.example.myadherence.screens.leaderboard

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController

@Composable
fun LeaderboardScreen(
    navController: NavController,
    viewModel: LeaderboardViewModel = hiltViewModel()
)
{
    val users = viewModel.users

    Column(
        modifier = Modifier.padding(32.dp).fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(8.dp),

    ) {
        Text(
            text = "Leaderboard",
            fontSize = 21.sp,
            fontStyle = FontStyle.Italic,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )

        LazyColumn{
            items(users) { user ->
                if(!user.optOutLeaderboard) {
                    Row (
                        horizontalArrangement = Arrangement.spacedBy(10.dp)

                    ) {
                        Text(user.nickname, fontSize = 16.sp)
                        Text(user.adherenceScore.toString(), fontSize = 16.sp, fontStyle = FontStyle.Italic)
                    }
                }
            }
        }

        Button(onClick = { viewModel.changeLeaderboardPreference()}) {
            Text(text = "Opt In/Out", fontSize= 15.sp)
        }


    }

    // Calls 'getUsers()' when composition is started.
    LaunchedEffect(Unit) {
        viewModel.getUsers()
        viewModel.getUserDetails()
    }
}