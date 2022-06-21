package com.example.toothpaste.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.toothpaste.R
import com.example.toothpaste.model.Pharmacy

class PharmacyAdapter : RecyclerView.Adapter<PharmacyAdapter.PharmacyViewHolder> {
    private lateinit var context: Context
    private lateinit var pharmacyList: MutableList<Pharmacy>

    constructor(context:Context , pharmacyList:MutableList<Pharmacy>){
        this.context=context
        this.pharmacyList=pharmacyList
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PharmacyViewHolder {
        val view= LayoutInflater.from(parent.context).inflate(R.layout.pharmacy_layout,parent,false)
        return PharmacyViewHolder(view)
    }

    override fun getItemCount(): Int =pharmacyList.size

    override fun onBindViewHolder(holder: PharmacyViewHolder, position: Int) {
        holder.pharmacyNameTxt.setText(pharmacyList[position].pharmacy_name)
    }

    fun setPharmList(pharmList:MutableList<Pharmacy>){
//        pharmacyList.clear()
//        pharmacyList.addAll(pharmList)
        pharmacyList=pharmList
        notifyDataSetChanged()
    }

    inner class PharmacyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val pharmacyNameTxt=itemView.findViewById<TextView>(R.id.pharmacyNameTxt)
    }
}