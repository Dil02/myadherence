package com.example.myadherence.model.service

import com.example.myadherence.model.User

// Defines an interface:
interface AccountService {
    fun register(email: String, password: String, nickname: String, onResult: (Throwable?) -> Unit)
    fun authenticate(email: String, password: String, onResult: (Throwable?) -> Unit)
    fun loggedIn(): Boolean
    fun signOut()
    fun getUserDetails(): User
    fun getUserID(): String
}