package com.example.toothpaste.ui.pharmacy

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.toothpaste.R
import com.example.toothpaste.adapter.PharmacyAdapter
import com.example.toothpaste.databinding.FragmentPharmacyBinding
import com.example.toothpaste.model.Medicine
import com.example.toothpaste.model.Pharmacy

class PharmacyFragment : Fragment(), SearchView.OnQueryTextListener {
    private val pharmResult: MutableList<Pharmacy> = mutableListOf<Pharmacy>() //
    private lateinit var medicine:Medicine
    private lateinit var viewDataBinding: FragmentPharmacyBinding
    private  var viewModel: PharmacyViewModel?=null
    private lateinit var linearLayoutManager: LinearLayoutManager
    private var pharmList= mutableListOf<Pharmacy>()
    private val pharmAdapter: PharmacyAdapter by lazy { PharmacyAdapter(requireContext(),pharmList) }
//    private val pharmSearchAdapter: PharmacyAdapter by lazy { PharmacyAdapter(requireContext(),pharmResult) }//
    private var isEmpty=true //


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        medicine= requireArguments().get("medicine") as Medicine
        viewModel= ViewModelProvider(this).get(PharmacyViewModel::class.java) // if error happened here remove 'this' and add requireActivity()

    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        viewDataBinding= DataBindingUtil.inflate(inflater, R.layout.fragment_pharmacy, container, false)
        viewDataBinding.lifecycleOwner = this.viewLifecycleOwner
        setHasOptionsMenu(true)
        Log.d("SEARCH_RESULT", medicine.name!!)
        Log.d("SEARCH_RESULT", medicine.key!!)

        viewDataBinding.pharmacyToolbar.title="Pharmacy"

//        viewModel!!.getAllPharmacy(medicine.key!!)         // not worked
//        viewModel!!.getAllPharmacyByName(medicine.name!!)  // not worked
//        viewModel!!.getAllPharmacyByValue(medicine.key!!)  // not worked
        viewModel!!.getAllPharmacyByKeyAndValue(medicine.key!!,medicine.name!!)

        linearLayoutManager= LinearLayoutManager(context, LinearLayoutManager.VERTICAL,false)
        viewDataBinding.pharmacyRecycler.layoutManager=linearLayoutManager
        viewDataBinding.pharmacyRecycler.setHasFixedSize(true)
        viewDataBinding.pharmacyRecycler.adapter=pharmAdapter

        viewModel!!.pharmacies.observe(viewLifecycleOwner, object : Observer<MutableList<Pharmacy>> {
            override fun onChanged(pharmacies: MutableList<Pharmacy>?) {
                Log.d("ddd","pharmacies called")
                pharmList.clear()
//                if (isEmpty) {
                    pharmList.addAll(pharmacies!!)
                    pharmAdapter.notifyDataSetChanged()
//                    pharmAdapter.setPharmList(pharmList)
//                }
            }
        })



        viewModel!!.resultSearchPharmacy.observe(viewLifecycleOwner, object : Observer<MutableList<Pharmacy>> {
            override fun onChanged(searchReslut: MutableList<Pharmacy>?) {
                pharmResult.clear()
                pharmResult.addAll(searchReslut!!)
                pharmAdapter.setPharmList(pharmResult)
            }

        })
        viewDataBinding.pharmacySearview.setOnQueryTextListener(this)

        return viewDataBinding.root
    }



    override fun onDestroy() {
        super.onDestroy()
//        if(viewModel!!.pharmacies.value!=null)
//          viewModel!!.pharmacies.value!!.clear()
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        return false
    }

    override fun onQueryTextChange(search: String?): Boolean {
        if (search.isNullOrEmpty()) {
//            isEmpty=true
//            listOfSymptoms.clear()
            pharmAdapter.setPharmList(pharmList)
            Log.d("ddd",pharmList.toString())
        }else {
//            isEmpty=false
            Log.d("ddd",pharmList.toString())
            viewModel!!.searchPharmacy(search!!)
        }

        return false
    }
}