package com.example.toothpaste.ui.sample

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.toothpaste.model.Medicine
import com.example.toothpaste.model.Sample
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class SampleViewModel : ViewModel() {
    val medicines = MutableLiveData<MutableList<String>>()
    private var medicineRef = FirebaseDatabase.getInstance().reference.child("Medicine")
    private var sampleRef = FirebaseDatabase.getInstance().reference.child("Sample")
    val samples = MutableLiveData<MutableList<Sample>>()


    fun getAllMedicine() {
        medicineRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onCancelled(error: DatabaseError) {

            }

            override fun onDataChange(snapshot: DataSnapshot) {
                val medicineResult = mutableListOf<String>()
                snapshot.children.forEach {
                    medicineResult.add(it.getValue(Medicine::class.java)!!.name!!)
                }
                medicines.value = medicineResult
            }
        })
    }

    fun submitSample(sample:Sample){
        Log.d("ddd",sample.toString())
        val key = sampleRef.push().key
        sample.sampleUid=key
        if (sample.uid == null )
            return
        sampleRef.child(key!!).setValue(sample).addOnCompleteListener {

        }
    }
    fun getAllSample(){
        sampleRef.orderByChild("uid").equalTo(FirebaseAuth.getInstance().uid).addValueEventListener(
            object : ValueEventListener {
                override fun onCancelled(error: DatabaseError) {

                }

                override fun onDataChange(snapshot: DataSnapshot) {
                    val sampleResult= mutableListOf<Sample>()
                    snapshot.children.forEach {
                        sampleResult.add(0,it.getValue(Sample::class.java)!!)
                    }
                    samples.value=sampleResult
                }
            })

    }
}