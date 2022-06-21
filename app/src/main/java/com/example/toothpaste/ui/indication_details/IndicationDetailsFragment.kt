package com.example.toothpaste.ui.indication_details

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.toothpaste.R
import com.example.toothpaste.adapter.MedSympAdapter
import com.example.toothpaste.adapter.SearchAdapter
import com.example.toothpaste.databinding.FragmentIndicationDetailsBinding
import com.example.toothpaste.model.Medicine
import com.example.toothpaste.model.Symptoms
import com.example.toothpaste.utils.Constants
import kotlinx.android.synthetic.main.fragment_indication_details.*


class IndicationDetailsFragment : Fragment(), MedSympAdapter.MedSympListener {

    private lateinit var linearLayoutManager: LinearLayoutManager
    private lateinit var viewDataBinding: FragmentIndicationDetailsBinding
    private  var symptoms:Symptoms?=null
    private  var viewModel: IndicationDetailsViewModel ?=null

    private var medList= mutableListOf<Medicine>()
    private val medSympAdapter: MedSympAdapter by lazy { MedSympAdapter(requireContext(),medList) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel=ViewModelProvider(this).get(IndicationDetailsViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewDataBinding= DataBindingUtil.inflate(inflater, R.layout.fragment_indication_details, container, false)
        viewDataBinding.lifecycleOwner = this.viewLifecycleOwner
        setHasOptionsMenu(true)
//        viewModel = ViewModelProvider.AndroidViewModelFactory.getInstance(requireActivity().application).create(
//            IndicationDetailsViewModel::class.java)

        if (symptoms==null){
            if (!(Constants.symptoms==null)){
                symptoms= Constants.symptoms!!
                Toast.makeText(context, "this", Toast.LENGTH_SHORT).show()
                viewModel!!.setSymtoms(symptoms!!)
                Constants.symptoms=null
            }
        }
        linearLayoutManager= LinearLayoutManager(context, LinearLayoutManager.VERTICAL,false)
        viewDataBinding.medicineSympRecycler.layoutManager=linearLayoutManager
        viewDataBinding.medicineSympRecycler.setHasFixedSize(true)

        viewDataBinding.medicineSympRecycler.adapter=medSympAdapter
        viewModel!!.symptoms.observe(viewLifecycleOwner, object : Observer<Symptoms> {
            override fun onChanged(symptoms: Symptoms?) {
                Log.d("ddd", symptoms.toString())
                medList.clear()
                if (symptoms!!.listOfMedicine==null)
                    return
                medList.addAll(symptoms!!.listOfMedicine!!)
                medSympAdapter.notifyDataSetChanged()
            }
        })
        medSympAdapter.addMedSympListener(this)


//        Log.d("ddd", symptoms.toString())

        return viewDataBinding.root
    }

    override fun medDetails(med: Medicine) {
        val bundle = bundleOf("medicine" to med)
        requireView().findNavController().navigate(R.id.action_indicationDetailsFragment_to_pharmacyFragment, bundle)
    }


}