package com.example.toothpaste.ui.visit

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.toothpaste.model.VisitUser
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class VisitViewModel : ViewModel() {
    private val visitRef= FirebaseDatabase.getInstance().reference.child("VisitUser")
    val visits= MutableLiveData<MutableList<VisitUser>>()

    fun getAllVisits(){
        val visitResult= mutableListOf<VisitUser>()
        visitRef.child(FirebaseAuth.getInstance().uid!!).addListenerForSingleValueEvent(object :
            ValueEventListener {
            override fun onCancelled(error: DatabaseError) {

            }
            override fun onDataChange(snapshot: DataSnapshot) {
                visitResult.clear()
                snapshot.children.forEach {
                    val visitUser=it.getValue(VisitUser::class.java)
                    visitResult.add(0,visitUser!!)
                }
                visits.value=visitResult
            }
        })
    }

    fun updateVisitDate(visitUser: VisitUser) {
        if (visitUser==null)
            return
        visitRef.child(visitUser.uid!!).child(visitUser.visit_key!!).setValue(visitUser).addOnCompleteListener {

        }
    }
}