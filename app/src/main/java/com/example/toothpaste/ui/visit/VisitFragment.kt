package com.example.toothpaste.ui.visit

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.toothpaste.R
import com.example.toothpaste.adapter.VisitAdapter
import com.example.toothpaste.databinding.FragmentVisitBinding
import com.example.toothpaste.model.VisitUser
import com.example.toothpaste.ui.pharmacy.PharmacyViewModel


class VisitFragment : Fragment(), VisitAdapter.AddVisitListener {

    private lateinit var visitTypeArray: Array<String>
    private lateinit var viewModel: VisitViewModel
    private lateinit var viewDataBinding: FragmentVisitBinding
    private var visitList= mutableListOf<VisitUser>()
    private val visitAdapter: VisitAdapter by lazy { VisitAdapter(requireContext(),visitList) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel= ViewModelProvider(this).get(VisitViewModel::class.java) // if error happened here remove 'this' and add requireActivity()

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        viewDataBinding= DataBindingUtil.inflate(inflater, R.layout.fragment_visit, container, false)
        viewDataBinding.lifecycleOwner = this.viewLifecycleOwner
        setHasOptionsMenu(true)

        viewDataBinding.visitsRecycyler.setHasFixedSize(true)
        viewDataBinding.visitsRecycyler.adapter=visitAdapter
        visitAdapter.addVisitListener(this)

//        val f2f="Remote"
//        visitTypeArray= requireContext().resources.getStringArray(R.array.visit_type)
//        val position=visitTypeArray.indexOf(f2f)
//        Log.d("ddd", position.toString())
//
        
        viewModel.getAllVisits()
        
        viewModel.visits.observe(viewLifecycleOwner, object : Observer<MutableList<VisitUser>> {
            override fun onChanged(visits: MutableList<VisitUser>?) {
                Toast.makeText(context, visits!!.size.toString(), Toast.LENGTH_SHORT).show()
                visitList.clear()
                visitList.addAll(visits!!)
                visitAdapter.notifyDataSetChanged()
            }
        })
        return viewDataBinding.root
    }

    override fun visitTypeListener(visitUser: VisitUser) {
        Toast.makeText(context, visitUser.toString(), Toast.LENGTH_LONG).show()
        Log.d("ddd", visitUser.toString())
        viewModel.updateVisitDate(visitUser)
    }

}