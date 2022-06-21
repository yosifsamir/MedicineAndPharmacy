package com.example.toothpaste.ui.event

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.toothpaste.R
import com.example.toothpaste.adapter.EventAdapter
import com.example.toothpaste.databinding.FragmentEventBinding
import com.example.toothpaste.model.Event


class EventFragment : Fragment(), EventAdapter.AddEventListener {

    private lateinit var viewModel: EventViewModel
    private lateinit var viewDataBinding: FragmentEventBinding
    private lateinit var linearLayoutManager:LinearLayoutManager
    private var eventList= mutableListOf<Event>()
    private val eventAdapter: EventAdapter by lazy { EventAdapter(requireContext(),eventList) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel= ViewModelProvider(this).get(EventViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        viewDataBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_event, container, false)
        linearLayoutManager= LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false)
        viewDataBinding.eventsRecycler.layoutManager=linearLayoutManager
        viewDataBinding.eventsRecycler.setHasFixedSize(true)
        viewDataBinding.eventsRecycler.adapter=eventAdapter

        eventAdapter.addRegisterClickListener(this)

        viewModel.events.observe(viewLifecycleOwner,
            Observer { events ->
                eventList.clear()
                //                if (isEmpty) {
                eventList.addAll(events!!)
                eventAdapter.notifyDataSetChanged()
            })

        viewModel.getAllEvents()

        return viewDataBinding.root
    }

    override fun addUserEventListener(event: Event) {
        viewModel.addUserForEvent(event)
    }

    override fun removeUserEventListener(event: Event) {
        viewModel.removeUserForEvent(event)

    }


}