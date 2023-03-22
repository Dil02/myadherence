package com.example.myadherence.model

// Defines a class for the User:
data class User (
    val id: String = "",
    val email: String = "",
    val nickname: String = "",
    val adherenceScore: Int = 0
)