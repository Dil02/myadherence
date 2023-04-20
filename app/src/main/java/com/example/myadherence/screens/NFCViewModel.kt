package com.example.myadherence.screens

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

class NFCViewModel() : ViewModel() {

    // Declares a mutable state.
    private val _inputText = mutableStateOf("")
    val inputText: State<String> = _inputText

    // This function updates the '_inputText' state based on the value passed.
    fun onInputTextChange(text: String) {
        _inputText.value = text
    }

}