package com.example.myadherence.screens.leaderboard

import androidx.compose.runtime.mutableStateMapOf
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

    /* Declares a map (mutable state type) of the User type indexed using a String value.
    This is observed by the LeaderboardScreen composable function in 'LeaderboardScreen.kt'. */
    var users = mutableStateMapOf<String, User>()
        private set

    // This function is used to inform the account service to fetch a single document relating to the current user.
    private fun getUserDetails() {
        accountService.getUserDetails() {
            users[accountService.getUserID()] = it
        }
    }

    // This function updates the 'users' map based on the User object being passed.
    private fun onDocumentEvent(user: User) {
        users[user.id] = user
    }

    // This function instructs the accountService to fetch all the current user documents.
    fun getUsers() {
        accountService.getUsers(:: onDocumentEvent)
    }

    // This function informs the accountService to update the 'optOutLeaderboard' property for the current user.
    fun changeLeaderboardPreference() {
        if(users[accountService.getUserID()]!!.optOutLeaderboard) {
            accountService.updateLeaderboardPreference(accountService.getUserID(),false)
        }
        else {
            accountService.updateLeaderboardPreference(accountService.getUserID(),true)
        }

        getUserDetails()
    }
}