package com.example.toothpaste.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.toothpaste.R
import com.example.toothpaste.model.Sample

class SampleAdapter(private val sampleList: MutableList<Sample>) : RecyclerView.Adapter<SampleAdapter.SampleViewHolder>() {



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SampleViewHolder {
        val view= LayoutInflater.from(parent.context).inflate(R.layout.sample_layout,parent,false)
        return SampleViewHolder(view)
    }

    override fun getItemCount(): Int =sampleList.size

    override fun onBindViewHolder(holder: SampleViewHolder, position: Int) {
        holder.sampleMedicineTxt.text=sampleList[position].medicineName
        holder.sampleQuantityTxt.text=sampleList[position].medicineQuantitiy.toString()
    }


    inner class SampleViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val sampleMedicineTxt=itemView.findViewById<TextView>(R.id.sampleMedicineTxt)
        val sampleQuantityTxt=itemView.findViewById<TextView>(R.id.sampleQuantityTxt)
    }

}