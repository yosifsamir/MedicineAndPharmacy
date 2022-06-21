package com.example.toothpaste.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.toothpaste.R
import com.example.toothpaste.model.Medicine
import com.example.toothpaste.model.Symptoms

class MedSympAdapter : RecyclerView.Adapter<MedSympAdapter.MedSympViewHolder> {
    private lateinit var context:Context
    private lateinit var medList: MutableList<Medicine>
    private lateinit var medSympListener: MedSympAdapter.MedSympListener


    constructor(context: Context,medList: MutableList<Medicine>){
        this.context=context
        this.medList=medList
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MedSympViewHolder {
        val view= LayoutInflater.from(parent.context).inflate(R.layout.med_symp_layout,parent,false)
        return MedSympViewHolder(view)
    }

    override fun getItemCount(): Int =medList.size

    override fun onBindViewHolder(holder: MedSympViewHolder, position: Int) {
        holder.medSympTxt.setText(medList[position].name)
        holder.itemView.setOnClickListener{
            Log.d("search", medList[position].toString())
            medSympListener.medDetails(medList[position])
        }
    }
    fun addMedSympListener(medSympListener: MedSympAdapter.MedSympListener){
        this.medSympListener=medSympListener
    }

    inner class MedSympViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val medSympTxt=itemView.findViewById<TextView>(R.id.medSympTxt)
    }

    interface MedSympListener{
        fun medDetails(med: Medicine)
    }

}