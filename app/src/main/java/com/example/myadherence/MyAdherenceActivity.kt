package com.example.myadherence

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.ExperimentalMaterialApi
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.HiltAndroidApp

@AndroidEntryPoint // Enables Hilt to be able to provide dependencies
// The main activity
class MyAdherenceActivity : ComponentActivity() {
    // This function is called when the activity is created and initialises 'MyAdherenceApp'.
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            MyAdherenceApp()
        }
    }
}