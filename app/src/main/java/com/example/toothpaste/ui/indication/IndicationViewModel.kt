package com.example.toothpaste.ui.indication

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.toothpaste.model.Symptoms
import com.example.toothpaste.utils.Event
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class IndicationViewModel : ViewModel() {
    private val refSym= FirebaseDatabase.getInstance().reference.child("Symptoms")
    val mutableLiveDataResult=MutableLiveData<MutableList<Symptoms>>()
    val searchStatus=MutableLiveData<Event>()

    fun searchSymptoms(data:String){
        searchStatus.postValue(Event.LOADING)
        val resultList= mutableListOf<Symptoms>()
        when{
            data.isNullOrEmpty()-> {
//                searchEdt.setError("Enter Some Text For Searching")
//                return@setOnClickListener
                searchStatus.postValue(Event.ERROR)
                return
            }
        }
        if (data.length==0) {
            searchStatus.postValue(Event.ERROR)
            return
        }
        var search=data.toLowerCase()

        var firstLetter=search[0].toUpperCase().toString()

        var remainingLetters=search.substring(1)
        var search2=firstLetter+remainingLetters
        refSym.orderByChild("symptomsName").startAt(search2).endAt(search2+"\uf8ff").addListenerForSingleValueEvent(
            object : ValueEventListener {
                override fun onCancelled(error: DatabaseError) {
                    searchStatus.postValue(Event.ERROR)
                }

                override fun onDataChange(snapshot: DataSnapshot) {
                    if (snapshot.exists()){
                        searchStatus.postValue(Event.SUCCESS)
                        resultList.clear()
                        snapshot.children.forEach({
                            val symptoms=it.getValue(Symptoms::class.java)
                            resultList.add(symptoms!!)
                        })
                        Log.d("SEARCH_RESULT",resultList.toString())
//                    searchAdapter.notifyDataSetChanged()
                        mutableLiveDataResult.value=resultList
                    }
                    else{
                        searchStatus.postValue(Event.ERROR)

                    }
                }

            })

    }
}