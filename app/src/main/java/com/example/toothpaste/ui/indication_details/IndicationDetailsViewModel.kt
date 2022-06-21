package com.example.toothpaste.ui.indication_details

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.toothpaste.model.Symptoms

class IndicationDetailsViewModel: ViewModel() {
    val symptoms=MutableLiveData<Symptoms>()



    fun setSymtoms(symptoms: Symptoms){
        this.symptoms.value=symptoms
    }



}