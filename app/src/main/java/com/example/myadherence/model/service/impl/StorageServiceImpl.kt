package com.example.myadherence.model.service.impl

import com.example.myadherence.model.Medicine
import com.example.myadherence.model.User
import com.example.myadherence.model.service.StorageService
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.ListenerRegistration
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class StorageServiceImpl @Inject constructor() : StorageService {
    private var listenerRegistration: ListenerRegistration? = null

    override fun addListener(
        userID: String,
        onDocumentEvent : (Medicine) -> Unit
    ) {
        val query = Firebase.firestore.collection("medicines").document(userID).collection("Medicine")

        listenerRegistration = query.addSnapshotListener { value, error ->
            if (error !=null) {
                println("error is happening!")
                return@addSnapshotListener
            }

            value?.documentChanges?.forEach {
                val medicine = it.document.toObject<Medicine>().copy(id = it.document.id)
                onDocumentEvent(medicine)
            }
        }
    }

    override fun removeListener() {
        listenerRegistration?.remove()
    }
}