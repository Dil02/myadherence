package com.example.myadherence.screens.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.myadherence.screens.create_account.CreateAccountViewModel

@Composable
fun HomeScreen(
    navController: NavController,
    viewModel: HomeViewModel
    //viewModel: HomeViewModel = hiltViewModel()
){
    val uiState by viewModel.uiState
    viewModel.getUserInfo()  //replaced the 'user.email' with 'uiState.email' etc...
    //val user = viewModel.getUser()

    Column(
        modifier = Modifier.padding(32.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ){
        Text(
            text = "Home Screen",
            fontSize = 26.sp,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )

        Text(
            text = "User email: " + uiState.email,
            fontSize = 16.sp,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )

        Text(
            text = "User id: " + uiState.id,
            fontSize = 16.sp,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )

        Text(
            text = "User nickname: " + uiState.nickname,
            fontSize = 16.sp,
            modifier = Modifier.align(Alignment.CenterHorizontally)
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
    }
}