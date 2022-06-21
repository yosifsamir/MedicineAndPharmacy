package com.example.toothpaste.model

import android.os.Parcel
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

//@Parcelize
data class Medicine(var key:String?=null,var name:String?=null): Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(key)
        parcel.writeString(name)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Medicine> {
        override fun createFromParcel(parcel: Parcel): Medicine {
            return Medicine(parcel)
        }

        override fun newArray(size: Int): Array<Medicine?> {
            return arrayOfNulls(size)
        }
    }
}