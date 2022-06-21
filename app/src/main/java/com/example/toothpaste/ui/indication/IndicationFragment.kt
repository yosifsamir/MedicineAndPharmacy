package com.example.toothpaste.ui.indication

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.toothpaste.R
import com.example.toothpaste.adapter.SearchAdapter
import com.example.toothpaste.databinding.FragmentIndicationBinding
import com.example.toothpaste.model.Symptoms
import com.example.toothpaste.ui.indication_details.IndicationDetailsViewModel
import com.example.toothpaste.utils.Constants
import com.example.toothpaste.utils.Event
import com.google.android.material.snackbar.Snackbar


class IndicationFragment : Fragment(), SearchView.OnQueryTextListener,
    SearchAdapter.SympSearchListener {

    private lateinit var viewModel: IndicationViewModel
    private lateinit var viewDataBinding:FragmentIndicationBinding
    private var listOfSymptoms= mutableListOf<Symptoms>()
    private val searchAdapter: SearchAdapter by lazy { SearchAdapter(listOfSymptoms,requireContext()) }
    private lateinit var linearLayoutManager: LinearLayoutManager
    private var isEmpty=true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel=ViewModelProvider(requireActivity()).get(IndicationViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        viewDataBinding= DataBindingUtil.inflate(inflater, R.layout.fragment_indication, container, false)
        viewDataBinding.lifecycleOwner = this.viewLifecycleOwner
        setHasOptionsMenu(true)
        linearLayoutManager= LinearLayoutManager(context, LinearLayoutManager.VERTICAL,false)
        viewDataBinding.searchRecycler.layoutManager=linearLayoutManager
        viewDataBinding.searchRecycler.setHasFixedSize(true)

        viewDataBinding.searchRecycler.adapter=searchAdapter

        viewModel.mutableLiveDataResult.observe(viewLifecycleOwner, object : Observer<MutableList<Symptoms>> {
            override fun onChanged(symptomsList: MutableList<Symptoms>?) {
                // indicationAdapter.submitList(symptomsList)
                listOfSymptoms.clear()
                if (!isEmpty)
                    listOfSymptoms.addAll(symptomsList!!)
                searchAdapter.notifyDataSetChanged()
                Log.d("ddd",symptomsList.toString())
            }
        })
        viewModel.searchStatus.observe(viewLifecycleOwner, object : Observer<Event> {
            override fun onChanged(event: Event?) {
                when{
                    event==Event.LOADING -> viewDataBinding.searchProgressBar.visibility=View.VISIBLE
                    event==Event.SUCCESS -> viewDataBinding.searchProgressBar.visibility=View.GONE
                    event==Event.ERROR -> {
                        viewDataBinding.searchProgressBar.visibility = View.GONE
                        Snackbar.make(requireContext(),viewDataBinding.searchConstraint,"There is no such data", Snackbar.LENGTH_SHORT).show()

                    }
                }
            }
        })

        viewDataBinding.searview.setOnQueryTextListener(this)
        searchAdapter.addSympListener(this)


        return viewDataBinding.root

    }

    override fun onQueryTextSubmit(query: String?): Boolean {

        return false
    }

    override fun onQueryTextChange(search: String?): Boolean {
        if (search.isNullOrEmpty()) {
            isEmpty=true
            listOfSymptoms.clear()
            searchAdapter.notifyDataSetChanged()
        }else {
            isEmpty=false
            viewModel.searchSymptoms(search!!)
        }
        return false
    }

    override fun sympDetails(symp: Symptoms) {
        Constants.symptoms=symp
        findNavController().navigate(R.id.action_indicationFragment_to_indicationDetailsFragment)
    }


}