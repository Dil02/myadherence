package com.example.myadherence.model.service.impl

import android.app.DownloadManager.Query
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.myadherence.model.Dose
import com.example.myadherence.model.Medicine
import com.example.myadherence.model.User
import com.example.myadherence.model.service.AccountService
import com.example.myadherence.screens.home.HomeViewModel
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.firestore.ktx.toObjects
import com.google.firebase.ktx.Firebase
import javax.inject.Inject

/* This class was sampled from: https://github.com/FirebaseExtended/make-it-so-android/blob/v1.0.0/app/src/main/java/com/example/makeitso/model/service/impl/AccountServiceImpl.kt,
 based on this tutorial: https://firebase.blog/posts/2022/05/adding-firebase-auth-to-jetpack-compose-app

 Firebase Authentication documentation: https://firebase.google.com/docs/auth/android/password-auth
 The purpose of this class is to carry out Firebase Authentication tasks such as registering a user and signing them in.
*/

// Defines the implementation of the AccountService interface:
class AccountServiceImpl @Inject constructor() : AccountService {

    // Sampled methods include:
    //*****************************************

    // This function attempts to sign in the user with their email address and password.
    override fun authenticate(email: String, password: String, onResult: (Throwable?) -> Unit) {
        Firebase.auth.signInWithEmailAndPassword(email,password)
            .addOnCompleteListener { onResult(it.exception)}
    }

    // This function signs out the current user.
    override fun signOut() {
        Firebase.auth.signOut()
    }

    // This function returns a Boolean value depending on if the user is logged in.
    override fun loggedIn() : Boolean {
        return (Firebase.auth.currentUser!=null)
    }

    // This function returns the current user's unique ID.
    override fun getUserID() : String {
        return Firebase.auth.currentUser?.uid.orEmpty()
    }

    //*****************************************


    /* This function registers a new user with the email address and password they have provided.
       It also stores their nickname within a new document on cloud firestore. */
    override fun register(
        email: String,
        password: String,
        nickname: String,
        onResult: (Throwable?) -> Unit
    ) {
        Firebase.auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener{onResult(it.exception)
                val userID = Firebase.auth.currentUser?.uid
                if (userID != null) {
                    val data = hashMapOf(
                        "nickname" to nickname,
                        "adherenceScore" to 0,
                        "optOutLeaderboard" to false,
                    )
                    Firebase.firestore.collection("users").document(userID).set(data)
                }
            }
    }


    // This function fetches a single User document based on the current user's id.
    override fun getUserDetails(onSuccess: (User) -> Unit){
        val id: String? = Firebase.auth.currentUser?.uid
        Firebase.firestore.collection("users").document(id!!).get()
            .addOnSuccessListener { document ->
                // Transforms a single 'User' document to an object of the User type.
                val user = document.toObject<User>()?.copy(id = document.id)
                onSuccess(user ?: User()) // Returns the user object to where the getUserDetails function was called.
            }
    }

    // This function fetches all the User documents. NEED TO CHANGE THIS FUNCTION POTENTIALLY!!!!
    override fun getUsers(onDocumentEvent: (User) -> Unit) {
        Firebase.firestore.collection("users").orderBy("adherenceScore",com.google.firebase.firestore.Query.Direction.ASCENDING).get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    val user = document.toObject<User>().copy(id = document.id)
                    onDocumentEvent(user)
                }
            }
    }

    // This function updates the 'optOutLeaderboard' property of a single User document based on the userID and value provided.
    override fun updateLeaderboardPreference(userID: String, value: Boolean) {
        Firebase.firestore.collection("users").document(userID).update("optOutLeaderboard",value)
            .addOnSuccessListener {
                println("Written successfully")
            }
    }

    // This function updates the 'adherenceScore' property of a single User document based on the userID and value provided.
    override fun updateAdherenceScore(userID: String, value: Int) {
        Firebase.firestore.collection("users").document(userID).update("adherenceScore",value)
            .addOnSuccessListener {
                println("Written Successfully")
            }
    }

}