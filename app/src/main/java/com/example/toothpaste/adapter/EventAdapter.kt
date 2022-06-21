package com.example.toothpaste.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.toothpaste.R
import com.example.toothpaste.model.Event
import com.google.firebase.auth.FirebaseAuth
import java.text.SimpleDateFormat
import java.util.*

class EventAdapter(private var context: Context, private var eventList: MutableList<Event>) :
    RecyclerView.Adapter<EventAdapter.EventViewHolder>() {

    private lateinit var addEventListener:AddEventListener

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventViewHolder {
        val view= LayoutInflater.from(parent.context).inflate(R.layout.event_layout,parent,false)
        return EventViewHolder(view)
    }

    override fun getItemCount(): Int =eventList.size

    override fun onBindViewHolder(holder: EventViewHolder, position: Int) {
        holder.eventNameTxt.text=eventList[position].eventName
        holder.eventPlaceTxt.text=eventList[position].eventPlace
        val date=Date(eventList[position].eventDate!!)
        val formatter = SimpleDateFormat("dd-M-yyyy hh:mm:ss", Locale.US) // check Locale.US later on .
        holder.eventDateTxt.text=formatter.format(date)
//        if (eventList[position].registerUsers == null){
//            eventList[position].registerUsers= HashMap()
//        }
        if (eventList[position].registerUsers.isNullOrEmpty()){
            holder.eventRegisterBtn.text="register"
        }else{
            if(eventList[position].registerUsers!!.containsKey(FirebaseAuth.getInstance().uid!!)){
                holder.eventRegisterBtn.text="unregister"
            }else{
                holder.eventRegisterBtn.text="register"
            }
        }


    }

    fun addRegisterClickListener(addEventListener: AddEventListener){
        this.addEventListener=addEventListener
    }

    inner class EventViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val eventNameTxt=itemView.findViewById<TextView>(R.id.eventNameTxt)
        val eventPlaceTxt=itemView.findViewById<TextView>(R.id.eventPlaceTxt)
        val eventDateTxt=itemView.findViewById<TextView>(R.id.eventDateTxt)
        val eventRegisterBtn=itemView.findViewById<TextView>(R.id.eventRegisterBtn)

        init {
            eventRegisterBtn.setOnClickListener {
                if (eventRegisterBtn.text == "register"){
                    addEventListener.addUserEventListener(eventList[adapterPosition])
                    eventRegisterBtn.text="unregister"
                }else{
                    addEventListener.removeUserEventListener(eventList[adapterPosition])
                    eventRegisterBtn.text="register"

                }
            }
        }
    }

    interface AddEventListener{
        fun addUserEventListener(event:Event)
        fun removeUserEventListener(event:Event)
    }
}