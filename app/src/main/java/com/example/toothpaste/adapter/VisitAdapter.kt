package com.example.toothpaste.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import com.example.toothpaste.R
import com.example.toothpaste.model.VisitUser

class VisitAdapter(private val context:Context,private val visitList: MutableList<VisitUser>) : RecyclerView.Adapter<VisitAdapter.VisitViewHolder>() {


    private var visitTypeArray: Array<String>
    private lateinit var addVisitListener:AddVisitListener
    init {
        visitTypeArray=context.resources.getStringArray(R.array.visit_type)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VisitViewHolder {
        val view=LayoutInflater.from(parent.context).inflate(R.layout.visit_layout,parent,false)
        return VisitViewHolder(view)
    }

    override fun getItemCount(): Int =visitList.size

    override fun onBindViewHolder(holder: VisitViewHolder, position: Int) {
        holder.visitDateTxt.text=visitList[position].visit_date
//        if (visitList[position].visit_type in visitTypeArray){
//        }
        val position=visitTypeArray.indexOf(visitList[position].visit_type)
        Log.d("ddd", position.toString())
        holder.visitTypeSpinner.setSelection(position)
        
    }

    fun addVisitListener(addVisitListener: AddVisitListener){
        this.addVisitListener=addVisitListener
    }


    inner class VisitViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val visitDateTxt=itemView.findViewById<TextView>(R.id.visitDateTxt)
        val visitTypeSpinner=itemView.findViewById<Spinner>(R.id.visitTypeSpinner)
        val submitVisitBtn=itemView.findViewById<Button>(R.id.submitVisitBtn)
        init {
//            visitTypeSpinner.onItemSelectedListener{parent, view, position, id ->
//                Toast.makeText(context, "${adapterPosition}", Toast.LENGTH_SHORT).show()
//
//            }

            var userSelect=false
            visitTypeSpinner.setOnItemSelectedListener(object : AdapterView.OnItemSelectedListener {
                override fun onNothingSelected(parent: AdapterView<*>?) {
                }


                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    if (userSelect){
                        userSelect=false
                        Toast.makeText(context, "${adapterPosition}", Toast.LENGTH_SHORT).show()

                    }
                }

            })
            visitTypeSpinner.setOnTouchListener{view ,event->
                userSelect = true
                false
            }

            submitVisitBtn.setOnClickListener{
                var visit=visitList[adapterPosition]
                visit.visit_type=visitTypeSpinner.selectedItem.toString()
                addVisitListener.visitTypeListener(visit)
            }
        }


    }

    interface AddVisitListener{
        fun visitTypeListener(visitUser: VisitUser)
    }
}