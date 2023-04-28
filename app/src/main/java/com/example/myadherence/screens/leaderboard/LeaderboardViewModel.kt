package com.example.myadherence.screens.leaderboard

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.toMutableStateList
import androidx.navigation.NavController
import com.example.myadherence.HOME_SCREEN
import com.example.myadherence.model.Medicine
import com.example.myadherence.model.User
import com.example.myadherence.model.service.AccountService
import com.example.myadherence.screens.MyAdherenceViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LeaderboardViewModel @Inject constructor(
    private val accountService: AccountService
) : MyAdherenceViewModel()
{

    // Declares and initialises the state observed by the composable.
    var users = mutableStateListOf<User>()
        private set

    var currentUser = User()

    // This function is used to inform the account service to fetch a single document relating to the current user.
    fun getUserDetails() {
        accountService.getUserDetails() {
            currentUser = it
        }
    }


    // This function instructs the accountService to fetch all the current user documents.
    fun getUsers() {
        accountService.getUsers() {
            users = it.toMutableStateList()
        }
    }

    // This function informs the accountService to update the 'optOutLeaderboard' property for the current user.
    fun changeLeaderboardPreference() {
        if(currentUser.optOutLeaderboard) {
            accountService.updateLeaderboardPreference(accountService.getUserID(),false)
        }
        else {
            accountService.updateLeaderboardPreference(accountService.getUserID(),true)
        }

        getUserDetails()
        getUsers()
    }
}