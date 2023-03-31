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

/* This class was sampled from: https://github.com/FirebaseExtended/make-it-so-android/blob/v1.0.0/app/src/main/java/com/example/makeitso/model/service/impl/StorageServiceImpl.kt,
 based on this tutorial: https://firebase.blog/posts/2022/07/adding-cloud-firestore-to-jetpack-compose-app#saving-a-task

 The purpose of this class is to allow the system to be able to fetch and write data to Cloud Firestore collections.
*/
class StorageServiceImpl @Inject constructor() : StorageService {

    // Declares a Firebase listener
    private var listenerRegistration: ListenerRegistration? = null

    // This function is used to set up the listener.
    override fun addListener(
        userID: String,
        onDocumentEvent : (Medicine) -> Unit
    ) {
        // The 'query' variable contains the collection that we want to listen to for any changes.
        val query = Firebase.firestore.collection("medicines").document(userID).collection("Medicine")

        /* Initialises the listener, the listener is a callback that is fired when new data is available.
           Firestore begins to monitor the specified collection. When the listener is first initialised,
           all the data belonging to the collection is returned. Subsequent changes to the collection will result
           in only the particular documents which have been modified being returned.
        */
        listenerRegistration = query.addSnapshotListener { value, error ->
            if (error !=null) {
                println("error is happening!")
                return@addSnapshotListener
            }

            // Returns a list of the documents which have been modified (returns all when first initialised).
            // Also processes the documents which have been returned.
            value?.documentChanges?.forEach {
                // Transforms a single 'Medicine' document to an object of the Medicine type.
                val medicine = it.document.toObject<Medicine>().copy(id = it.document.id)
                onDocumentEvent(medicine) // Function is written in 'HomeViewModel.kt'.
            }
        }
    }

    // This function is used to deactivate the listener when it is no longer required.
    override fun removeListener() {
        listenerRegistration?.remove()
    }

    // This function fetches a Medicine document based on the document id provided.
    override fun getMedication(
        medicationID: String,
        onSuccess: (Medicine) -> Unit
    ) {
        val userID = Firebase.auth.currentUser?.uid
        if (userID != null) {
            // Accesses the current user's medicine collection and fetches the relevant document based on the medicationID.
            Firebase.firestore.collection("medicines").document(userID)
                .collection("Medicine").document(medicationID).get()
                .addOnSuccessListener { document ->
                    // Transforms a single 'Medicine' document to an object of the Medicine type.
                    val medication = document.toObject<Medicine>()?.copy(id = document.id)
                    onSuccess(medication ?: Medicine()) // Returns the medication object to where the getMedication function was called.
            }
        }
    }

    // This function updates a Medicine document based on the Medicine object provided.
    override fun updateMedication(
        medication: Medicine
    ) {
        val userID = Firebase.auth.currentUser?.uid
        if(userID != null) {
            // Note: Since the way object reflection works, the Medicine 'id' property will be saved inside the document as a field.
            Firebase.firestore.collection("medicines").document(userID)
                .collection("Medicine").document(medication.id).set(medication)
                .addOnSuccessListener {
                    println("Written successfully")
                }
        }
    }

    // This function adds a new Medicine document based on the Medicine object provided.
    override fun addMedication(
        medication: Medicine
    ) {
        val userID = Firebase.auth.currentUser?.uid
        if(userID != null)
        {
            Firebase.firestore.collection("medicines").document(userID)
                .collection("Medicine").add(medication)
                .addOnSuccessListener {
                    println("Written successfully")
                }
        }
    }

    // This function deletes a Medicine document based on the medicationID provided.
    override fun deleteMedication(
        medicationID: String
    ) {
        val userID = Firebase.auth.currentUser?.uid
        if(userID != null)
        {
            Firebase.firestore.collection("medicines").document(userID)
                .collection("Medicine").document(medicationID).delete()
                .addOnSuccessListener {
                    println("Deleted successfully")
                }
        }
    }
}

/* So essentially you do not modify the local state, instead you make changes (write) to the collection and documents on Firestore,
and the listener will notice these changes and update the local state. Composable functions notice that the
state has been updated and recompose themselves.
*/