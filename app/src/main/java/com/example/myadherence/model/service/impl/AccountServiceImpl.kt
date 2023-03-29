package com.example.myadherence.model.service.impl

import androidx.hilt.navigation.compose.hiltViewModel
import com.example.myadherence.model.User
import com.example.myadherence.model.service.AccountService
import com.example.myadherence.screens.home.HomeViewModel
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
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
                        "adherenceScore" to 0
                    )
                    Firebase.firestore.collection("users").document(userID).set(data)
                }
            }
    }

    // This function returns a User object containing the current user's information.
    override fun getUserDetails(): User {
        val id: String? = Firebase.auth.currentUser?.uid
        val email: String? = Firebase.auth.currentUser?.email
        if(email!=null && id!=null)
        {
            return User(id,email,"")
        }
        return User("There was no user","There was no user","There was no user")
    }

}