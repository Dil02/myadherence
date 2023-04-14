package com.example.myadherence.screens

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

class NFCViewModel() : ViewModel() {
    private val _inputText = mutableStateOf("")
    val inputText: State<String> = _inputText

    fun onInputTextChange(text: String)
    {
        _inputText.value = text
    }

}