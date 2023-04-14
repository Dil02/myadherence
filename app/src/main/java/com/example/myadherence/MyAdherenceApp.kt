package com.example.myadherence

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.*
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.myadherence.screens.NFCViewModel
import com.example.myadherence.screens.create_account.CreateAccountScreen
import com.example.myadherence.screens.create_account.CreateAccountViewModel
import com.example.myadherence.screens.dose.DoseScreen
import com.example.myadherence.screens.home.HomeScreen
import com.example.myadherence.screens.home.HomeViewModel
import com.example.myadherence.screens.leaderboard.LeaderboardScreen
import com.example.myadherence.screens.login.LoginScreen
import com.example.myadherence.screens.login.LoginViewModel
import com.example.myadherence.screens.medication.MedicationScreen
import com.example.myadherence.screens.medication_doses.MedicationDosesScreen
import com.example.myadherence.screens.welcome.WelcomeScreen
import com.example.myadherence.screens.welcome.WelcomeViewModel
import com.example.myadherence.ui.theme.MyAdherenceTheme
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.HiltAndroidApp


// Main app composable:
@Composable
fun MyAdherenceApp(tempViewModel: NFCViewModel){
    MyAdherenceTheme {
        // A surface container using the 'background' color from the theme
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colors.background
        ) {
            Navigation(tempViewModel)
        }
    }
}

// Navigation composable:
@Composable
fun Navigation(tempViewModel: NFCViewModel) {
    // NavController is the API used for navigation.
    val navController = rememberNavController()

    // Because of the way I am doing the hiltViewModel the home screen has to call getUserDetails everytime it recomposes.
    // Defining the relevant ViewModels:
//    val viewModel = hiltViewModel<CreateAccountViewModel>()
//    val viewModel1 = hiltViewModel<HomeViewModel>()
//    val viewModel2 = hiltViewModel<LoginViewModel>()
//    val viewModel3 = hiltViewModel<WelcomeViewModel>()

    // NavHost links the NavController with the navigation graph.
    NavHost(navController = navController, startDestination = WELCOME_SCREEN) {
        // This navigation graph states the composable functions that can be navigated to:
        composable(route = CREATE_ACCOUNT_SCREEN) {
            CreateAccountScreen(navController = navController)
        }
        composable(route = HOME_SCREEN) {
            HomeScreen(navController = navController, tempViewModel = tempViewModel)
        }
        composable(route = LOGIN_SCREEN) {
            LoginScreen(navController = navController)
        }
        composable(route = WELCOME_SCREEN) {
            if(Firebase.auth.currentUser?.uid != null) {
                HomeScreen(navController,tempViewModel)
                //HomeScreen(navController,viewModel1)
            }
            else
            {
                WelcomeScreen(navController = navController)
            }
        }

        composable(route = LEADERBOARD_SCREEN) {
            LeaderboardScreen(navController = navController)
        }

        // This composable contains an argument placeholder ('{medicationID}') in the route.
        composable(route = "$MEDICATION_SCREEN/{medicationID}", arguments = listOf(
            navArgument("medicationID") {
                type = NavType.StringType // Defines the type of the single argument
            }
        ) ) { entry -> //Extracts the argument from the NavBackStackEntry
            MedicationScreen(navController = navController, medicationID = entry.arguments?.getString("medicationID"))
        }

        composable(route = "$MEDICATION_DOSES_SCREEN/{medicationID}/{medicationName}", arguments = listOf(
            navArgument("medicationID") { type = NavType.StringType },
            navArgument("medicationName") { type = NavType.StringType }
        )) {entry ->
            MedicationDosesScreen(
                navController = navController,
                medicationID = entry.arguments?.getString("medicationID"),
                medicationName = entry.arguments?.getString("medicationName")
            )
        }
        composable(route = "$DOSE_SCREEN/{medicationID}/{medicationName}/{doseID}", arguments = listOf(
            navArgument("medicationID") { type = NavType.StringType },
            navArgument("medicationName") { type = NavType.StringType},
            navArgument("doseID") { type = NavType.StringType}
        )) {entry ->
            DoseScreen(
                navController = navController,
                medicationID = entry.arguments?.getString("medicationID") ,
                medicationName = entry.arguments?.getString("medicationName") ,
                doseID = entry.arguments?.getString("doseID"))
        }
    }
}