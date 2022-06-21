package com.example.toothpaste.adapter

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

import com.example.toothpaste.R
import com.example.toothpaste.model.Symptoms
import kotlinx.android.synthetic.main.search_layout.view.*

class SearchAdapter : RecyclerView.Adapter<SearchAdapter.SearchViewHolder> {

    private lateinit var sympList:MutableList<Symptoms>
    private lateinit var context:Context
    private lateinit var sympSearchListener: SympSearchListener

    constructor(sympList:MutableList<Symptoms>, context: Context){
        this.sympList=sympList
        this.context=context
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchViewHolder {
        val view= LayoutInflater.from(parent.context).inflate(R.layout.search_layout,parent,false)
        return SearchViewHolder(view)
    }

    override fun getItemCount(): Int = sympList.size

    override fun onBindViewHolder(holder: SearchViewHolder, position: Int) {
        Log.d("data", sympList[position].symptomsName.toString())
        holder.sympTxt.setText(sympList[position].symptomsName)
        holder.itemView.setOnClickListener{
            Log.d("search", sympList[position].toString())
            sympSearchListener.sympDetails(sympList[position])
        }
    }

    fun addSympListener(sympSearchListener: SympSearchListener){
        this.sympSearchListener=sympSearchListener
    }

    inner class SearchViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val sympTxt=itemView.findViewById<TextView>(R.id.sympSearchTxt)
    }

    interface SympSearchListener{
        fun sympDetails(symp: Symptoms)
    }
}