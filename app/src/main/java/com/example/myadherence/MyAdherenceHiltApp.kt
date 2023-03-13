package com.example.myadherence

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp // Triggers Hilt's code generation
class MyAdherenceHiltApp : Application() {}