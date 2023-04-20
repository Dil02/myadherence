package com.example.myadherence.model.service

import com.example.myadherence.model.Medicine
import com.example.myadherence.model.User

// Defines an interface:
interface AccountService {
    fun register(email: String, password: String, nickname: String, onResult: (Throwable?) -> Unit)
    fun authenticate(email: String, password: String, onResult: (Throwable?) -> Unit)
    fun loggedIn(): Boolean
    fun signOut()
    fun getUserDetails(onSuccess: (User) -> Unit)
    fun getUserID(): String
    fun getUsers(onDocumentEvent: (User) -> Unit)
    fun updateLeaderboardPreference(userID: String, value: Boolean)
    fun updateAdherenceScore(userID: String, value: Int)
}