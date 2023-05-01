package com.example.myadherence

import android.content.Intent
import android.nfc.NdefMessage
import android.nfc.NfcAdapter
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.sp
import com.example.myadherence.model.service.AccountService
import com.example.myadherence.model.service.StorageService
import com.example.myadherence.model.service.impl.AccountServiceImpl
import com.example.myadherence.model.service.impl.StorageServiceImpl
import com.example.myadherence.screens.MyAdherenceViewModel
import com.example.myadherence.screens.NFCViewModel
import com.example.myadherence.screens.home.HomeViewModel
import com.example.myadherence.ui.theme.MyAdherenceTheme
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.HiltAndroidApp

@AndroidEntryPoint // Enables Hilt to be able to provide dependencies
// The main activity
class MyAdherenceActivity : ComponentActivity() {
    val nfcViewModel: NFCViewModel by viewModels() // View Model created here so it can be updated by 'onNewIntent()' and observed by HomeScreen.

    // This function is called when the activity is created and initialises 'MyAdherenceApp'.
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyAdherenceApp(nfcViewModel)
        }
    }

    // This function is used to read an NFC tag.
    // onNewIntent is called whenever a suitable (NDEF_DISCOVERED) NFC intent occurs.
    // This function was adapted from: https://developer.android.com/guide/topics/connectivity/nfc/nfc
    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        if (NfcAdapter.ACTION_NDEF_DISCOVERED == intent.action) {
            intent.getParcelableArrayExtra(NfcAdapter.EXTRA_NDEF_MESSAGES)?.also { rawMessages ->
                val messages: List<NdefMessage> = rawMessages.map { it as NdefMessage }

                // Processes the messages array.
                var data: String =(String(messages[0].getRecords()[0].getPayload()))
                if(data.length>3) {
                    data=data.substring(3,data.length) // Removes the language code ' en' from the string.
                }
                else {
                    data=""
                }
                nfcViewModel.onInputTextChange(data) // Updates the NFCViewModel which can be accessed by the HomeScreen.
            }
        }
    }

}