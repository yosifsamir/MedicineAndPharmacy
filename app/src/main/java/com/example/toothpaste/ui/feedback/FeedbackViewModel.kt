package com.example.toothpaste.ui.feedback

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class FeedbackViewModel : ViewModel() {
    var stateLiveData=MutableLiveData<Boolean?>()
    private val feedbackRef=FirebaseDatabase.getInstance().reference.child("Feedback")
    val feedbacksMutableLiveData=MutableLiveData<MutableList<String>>()

    fun addFeedback(feedback: String) {
        val key=feedbackRef.push().key
        feedbackRef.child(FirebaseAuth.getInstance().uid!!).child(key!!).setValue(feedback).addOnCompleteListener {
            stateLiveData.value=true
            stateLiveData.value=null
        }
    }

    fun loadAllFeedback(){
        val uid=FirebaseAuth.getInstance().uid!!
        feedbackRef.child(uid).addValueEventListener(object : ValueEventListener {
            override fun onCancelled(error: DatabaseError) {

            }

            override fun onDataChange(snapshot: DataSnapshot) {
                val result= mutableListOf<String>()
                snapshot.children.forEach {
                    result.add(0,it.getValue(String::class.java)!!)
                }
                feedbacksMutableLiveData.value=result
            }
        })
    }
}