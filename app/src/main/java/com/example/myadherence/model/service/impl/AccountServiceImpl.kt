package com.example.myadherence.model.service.impl

import androidx.hilt.navigation.compose.hiltViewModel
import com.example.myadherence.model.User
import com.example.myadherence.model.service.AccountService
import com.example.myadherence.screens.home.HomeViewModel
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import javax.inject.Inject

// Defines the implementation of the AccountService interface:
class AccountServiceImpl @Inject constructor() : AccountService {
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

    override fun authenticate(email: String, password: String, onResult: (Throwable?) -> Unit) {
        Firebase.auth.signInWithEmailAndPassword(email,password)
            .addOnCompleteListener { onResult(it.exception)}
    }

    override fun signOut() {
        Firebase.auth.signOut()
    }

    override fun loggedIn() : Boolean {
        return (Firebase.auth.currentUser!=null)
    }

    override fun getUserDetails(): User {
        val id: String? = Firebase.auth.currentUser?.uid
        val email: String? = Firebase.auth.currentUser?.email
        if(email!=null && id!=null)
        {
            return User(id,email,"")
        }
        return User("There was no user","There was no user","There was no user")
    }

    override fun getUserID() : String {
        return Firebase.auth.currentUser?.uid.orEmpty()
    }
}