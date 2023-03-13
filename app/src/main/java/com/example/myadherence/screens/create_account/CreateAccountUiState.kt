package com.example.myadherence.screens.create_account

// Defines the Ui state of the Create Account screen:
data class CreateAccountUiState(
    val email: String = "",
    val password: String= "",
    val confirmPassword: String= "",
    val nickname: String = ""
)