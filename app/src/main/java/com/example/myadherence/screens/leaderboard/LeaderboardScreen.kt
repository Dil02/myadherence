package com.example.myadherence.screens.leaderboard

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.myadherence.screens.login.LoginViewModel

@Composable
fun LeaderboardScreen(
    navController: NavController,
    viewModel: LeaderboardViewModel = hiltViewModel()
)
{
    val users = viewModel.users

    Column(
        modifier = Modifier.padding(32.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(
            text = "Leaderboard",
            fontSize = 26.sp,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )

        Button(onClick = { viewModel.changeLeaderboardPreference()}) {
            Text(text = "Opt In/Out", fontSize= 15.sp)
        }

        LazyColumn{
            items(users.values.toList()) { user ->
                if(!user.optOutLeaderboard) {
                    Text(
                        text = user.nickname + " ---> " + user.adherenceScore.toString(),
                        fontSize = 18.sp
                    )
                }
            }
        }

    }

    // Calls 'getUsers()' when composition is started.
    LaunchedEffect(Unit) {
        viewModel.getUsers()
    }
}