package com.example.toothpaste.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.toothpaste.R

class FeedbackAdapter(private var context: Context, private var feedbackList: MutableList<String>)  : RecyclerView.Adapter<FeedbackAdapter.FeedbackViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FeedbackViewHolder {
        val view=LayoutInflater.from(parent.context).inflate(R.layout.feedback_layout,parent,false)
        return FeedbackViewHolder(view)
    }

    override fun getItemCount(): Int =feedbackList.size

    override fun onBindViewHolder(holder: FeedbackViewHolder, position: Int) {
        holder.feedbackTxt.text=feedbackList[position]
    }



    inner class FeedbackViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val feedbackTxt=itemView.findViewById<TextView>(R.id.feedbackTxt)
    }


}