package com.example.toothpaste.ui.pharmacy

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.toothpaste.model.Pharmacy
import com.example.toothpaste.model.Symptoms
import com.example.toothpaste.utils.Event
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class PharmacyViewModel : ViewModel() {
    val resultSearchPharmacy=MutableLiveData<MutableList<Pharmacy>>()
    val pharmacies= MutableLiveData<MutableList<Pharmacy>>()
    private val pharmRef=FirebaseDatabase.getInstance().reference.child("pharmacy")
//    fun getAllPharmacy(medicineKey:String){
//        if (medicineKey==null){
//            // handle this error here.
//            return
//        }
//        val resultList= mutableListOf<Pharmacy>()
//
//        pharmRef.orderByChild("medicines").equalTo(medicineKey).addListenerForSingleValueEvent(object : ValueEventListener {
//            override fun onCancelled(error: DatabaseError) {
//
//            }
//
//            override fun onDataChange(snapshot: DataSnapshot) {
//                if (snapshot.exists()==false){
//                    // handle this error by showing message or adding ProgressBar or something like that
//                    Log.d("SEARCH_RESULT_2","There is no data")
//
//                    return
//                }
////                searchStatus.postValue(Event.SUCCESS)
//                resultList.clear()
//                snapshot.children.forEach({
//                    val pharmacy=it.getValue(Pharmacy::class.java)
//                    resultList.add(pharmacy!!)
//                })
//                Log.d("SEARCH_RESULT_2",resultList.toString())
//                pharmacies.value=resultList
//            }
//        })
//    }
//
//    fun getAllPharmacyByName(name: String) {
//        if (name==null){
//            // handle this error here.
//            return
//        }
//        val resultList= mutableListOf<Pharmacy>()
//
//        pharmRef.orderByChild("medicines").equalTo(name).addListenerForSingleValueEvent(object : ValueEventListener {
//            override fun onCancelled(error: DatabaseError) {
//
//            }
//
//            override fun onDataChange(snapshot: DataSnapshot) {
//                if (snapshot.exists()==false){
//                    // handle this error by showing message or adding ProgressBar or something like that
//                    Log.d("SEARCH_RESULT_2","There is no data2")
//
//                    return
//                }
////                searchStatus.postValue(Event.SUCCESS)
//                resultList.clear()
//                snapshot.children.forEach({
//                    val pharmacy=it.getValue(Pharmacy::class.java)
//                    resultList.add(pharmacy!!)
//                })
//                Log.d("SEARCH_RESULT_2",resultList.toString())
//                pharmacies.value=resultList
//            }
//        })
//    }

//    fun getAllPharmacyByValue(medicineKey: String) {
//        if (medicineKey==null){
//            // handle this error here.
//            return
//        }
//        val resultList= mutableListOf<Pharmacy>()
//
//        pharmRef.orderByChild("medicines").equalTo(medicineKey).addListenerForSingleValueEvent(object : ValueEventListener {
//            override fun onCancelled(error: DatabaseError) {
//
//            }
//
//            override fun onDataChange(snapshot: DataSnapshot) {
//                if (snapshot.exists()==false){
//                    // handle this error by showing message or adding ProgressBar or something like that
//                    Log.d("SEARCH_RESULT_2","There is no data3")
//
//                    return
//                }
////                searchStatus.postValue(Event.SUCCESS)
//                resultList.clear()
//                snapshot.children.forEach({
//                    val pharmacy=it.getValue(Pharmacy::class.java)
//                    resultList.add(pharmacy!!)
//                })
//                Log.d("SEARCH_RESULT_2",resultList.toString())
//                pharmacies.value=resultList
//            }
//        })
//
//    }

    fun getAllPharmacyByKeyAndValue(key: String, name: String) {
//        searchStatus.postValue(Event.LOADING)
        if (key==null){
            // handle this error here.
//            searchStatus.postValue(Event.ERROR)
            return
        }
        val resultList= mutableListOf<Pharmacy>()

        pharmRef.orderByChild("medicines/$key/name").equalTo(name).addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onCancelled(error: DatabaseError) {
//                searchStatus.postValue(Event.ERROR)

            }

            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()==false){
                    // handle this error by showing message or adding ProgressBar or something like that
                    Log.d("SEARCH_RESULT_2","There is no data3")
//                    searchStatus.postValue(Event.ERROR)

                    return
                }
//                searchStatus.postValue(Event.SUCCESS)
                resultList.clear()
                snapshot.children.forEach({
                    val pharmacy=it.getValue(Pharmacy::class.java)
                    resultList.add(pharmacy!!)
                })
                Log.d("SEARCH_RESULT_2",resultList.toString())
                pharmacies.value=resultList
            }
        })

    }
    init {
        Log.d("SEARCH_RESULT_2","ViewModel initialized")
    }

    fun searchPharmacy(data:String){
//        searchStatus.postValue(Event.LOADING)
        val resultList= mutableListOf<Pharmacy>()
        when{
            data.isNullOrEmpty()-> {
//                searchEdt.setError("Enter Some Text For Searching")
//                return@setOnClickListener
//                searchStatus.postValue(Event.ERROR)
                return
            }
        }
        if (data.length==0) {
//            searchStatus.postValue(Event.ERROR)

            return
        }
        if (pharmacies.value == null)
        {
            return
        }
        pharmacies.value!!.forEach {
            if (it.pharmacy_name!!.startsWith(data,true)){
                resultList.add(it)
                Log.d("ddd", it.pharmacy_name!!)
            }
        }
        resultSearchPharmacy.value=resultList
    }
//    override fun onCleared() {
//        super.onCleared()
//        Log.d("SEARCH_RESULT_2","onCleared() is called" )
//        pharmacies.removeObserver {  }
//    }
}