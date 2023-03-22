package com.example.myadherence.screens.leaderboard

import androidx.navigation.NavController
import com.example.myadherence.HOME_SCREEN
import com.example.myadherence.screens.MyAdherenceViewModel
import javax.inject.Inject

class LeaderboardViewModel @Inject constructor(

) : MyAdherenceViewModel()
{
    fun goToHome(navController: NavController)
    {
        navController.navigate(HOME_SCREEN)
    }
}