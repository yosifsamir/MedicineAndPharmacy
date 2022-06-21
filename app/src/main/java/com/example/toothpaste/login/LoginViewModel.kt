package com.example.toothpaste.ui.login

import android.content.Intent
import android.util.Log
import android.util.Patterns
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.toothpaste.model.User
import com.example.toothpaste.notification.TokenModel
import com.example.toothpaste.utils.Constants
import com.example.toothpaste.utils.Event
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.example.toothpaste.utils.Constants.MAX_REG_NO_LENGTH
import com.example.toothpaste.utils.Constants.MIN_PASSWORD_LENGTH
import com.example.toothpaste.utils.Constants.MIN_PHONE_LENGTH
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.messaging.FirebaseMessaging

class LoginViewModel : ViewModel() {
    val loginStatus=MutableLiveData<Event>()
    val registerStatus=MutableLiveData<Event>()

    private val firebaseAuth = FirebaseAuth.getInstance()
    private val databaseReference = FirebaseDatabase.getInstance().getReference("users")

    fun loginUser(email: String, password: String) {
        if (email.isEmpty() || password.isEmpty()) {
            loginStatus.postValue(Event.ERROR)
        } else {
            loginStatus.postValue(Event.LOADING)
            firebaseAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener {
                if (it.isSuccessful) {
//                    loginStatus.postValue(Event.SUCCESS)
                    FirebaseMessaging.getInstance().token.addOnCompleteListener {itToken->
                        FirebaseDatabase.getInstance().reference
                            .child("Tokens")
                            .child(FirebaseAuth.getInstance().uid!!)
                            .setValue(TokenModel(FirebaseAuth.getInstance().uid, itToken.result))
                            .addOnSuccessListener {
                                Constants.token=itToken.result
                                loginStatus.postValue(Event.SUCCESS)
                            }
                    }
                }
                else {
                    loginStatus.postValue(Event.ERROR)
                }

            }
        }
    }

    fun registerUser(
        email: String,
        username: String,
        regNo: String,
        password: String,
        phoneNum: String
    ) {
        var error = if (email.isEmpty() || username.isEmpty() || password.isEmpty() || phoneNum.isEmpty()) {
            "Empty Strings"
        }
//        else if (regNo.length != MAX_REG_NO_LENGTH) {
//            "Invalid Registration Number"
//        } else if (password.length < MIN_PASSWORD_LENGTH) {
//            "Password to short"
//        } else if (phoneNum.length < MIN_PHONE_LENGTH) {
//            "Phone to short"
//        } else if (phoneNum.length > MIN_PHONE_LENGTH) {
//            "Phone to long"
//        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
//            "Not a valid Email"
//        }
        else null

        error?.let {
            registerStatus.postValue(Event.ERROR)
            return
        }
        registerStatus.postValue(Event.LOADING)



        firebaseAuth.createUserWithEmailAndPassword(email, password).addOnSuccessListener {
            val result1=it
            firebaseAuth.currentUser.sendEmailVerification().addOnCompleteListener {
                val uid=result1.user.uid
                val user = User(uid,email,username,regNo,phoneNum)
                databaseReference.child(uid).setValue(user).addOnCompleteListener {
//                    registerStatus.postValue(Event.SUCCESS)
                    FirebaseMessaging.getInstance().token
                        .addOnCompleteListener(OnCompleteListener<String?> { task ->
                            if (!task.isSuccessful) {
                                Log.w(
                                    "Token",
                                    "Fetching FCM registration token failed",
                                    task.exception
                                )
                                return@OnCompleteListener
                            }

                            // Get new FCM registration token
                            val token = task.result


                            Log.d("Token", token.toString())
                            if (task == null || task.result == null)
                                return@OnCompleteListener
                            Constants.token = token.toString()
                            FirebaseDatabase.getInstance().reference
                                .child("Tokens")
                                .child(FirebaseAuth.getInstance().uid!!)
                                .setValue(TokenModel(FirebaseAuth.getInstance().uid!!, token.toString()))
                                .addOnCompleteListener {
                                    registerStatus.postValue(Event.SUCCESS)
                                }
                        })

                }
            }
        }
    }


}