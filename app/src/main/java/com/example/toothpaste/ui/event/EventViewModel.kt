package com.example.toothpaste.ui.event

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.toothpaste.model.Event
import com.example.toothpaste.model.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class EventViewModel : ViewModel() {
    private val eventRef= FirebaseDatabase.getInstance().reference.child("Event")
    val events=MutableLiveData<MutableList<Event>>()

    fun getAllEvents(){
        val eventResult= mutableListOf<Event>()
        eventRef.limitToFirst(10).addListenerForSingleValueEvent(object :
            ValueEventListener {
            override fun onCancelled(error: DatabaseError) {

            }

            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()){
                    snapshot.children.forEach {
                        val event=it.getValue(Event::class.java)
                        eventResult.add(0,event!!)
                    }
                    events.value=eventResult
                }
            }
        })
    }

    fun addUserForEvent(event: Event) {
        FirebaseDatabase.getInstance().reference.child("users").child(FirebaseAuth.getInstance().uid!!).addListenerForSingleValueEvent(
            object : ValueEventListener {
                override fun onCancelled(error: DatabaseError) {

                }

                override fun onDataChange(snapshot: DataSnapshot) {
                    val user=snapshot.getValue(User::class.java)
                    val userEvent= hashMapOf(FirebaseAuth.getInstance().uid to user!!.username)
                    eventRef.child(event.eventKey!!).child("registerUsers").updateChildren(userEvent as Map<String, Any>).addOnCompleteListener {  }
                }
            })

    }

    fun removeUserForEvent(event: Event) {
        eventRef.child(event.eventKey!!).child("registerUsers").child(FirebaseAuth.getInstance().uid!!)
            .removeValue().addOnCompleteListener {  }
    }

}