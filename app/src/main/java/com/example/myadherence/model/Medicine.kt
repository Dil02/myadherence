package com.example.myadherence.model

data class Medicine (
    val id: String = "",
    val name: String = "",
    val knownSideEffects: String = "",
    val pillCount: Int = 0,
    val currentPillCount: Int = 0,
    val progress: Int = 0
)