package com.example.toothpaste.model

data class Symptoms(var keySymp:String?=null
                    ,var symptomsName:String?=null
                    ,var listOfMedicine: MutableList<Medicine>?=null)