package com.example.toothpaste.ui.sample

import android.os.Bundle
import android.text.InputFilter
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.toothpaste.R
import com.example.toothpaste.adapter.SampleAdapter
import com.example.toothpaste.databinding.FragmentSampleBinding
import com.example.toothpaste.model.Sample
import com.example.toothpaste.utils.InputFilterMinMax
import com.google.firebase.auth.FirebaseAuth


class SampleFragment : Fragment() {

    private lateinit var viewDataBinding: FragmentSampleBinding
    private lateinit var viewModel: SampleViewModel
    private var sampleList= mutableListOf<Sample>()
    private val sampleAdapter: SampleAdapter by lazy { SampleAdapter(sampleList) }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel =
            ViewModelProvider(this).get(SampleViewModel::class.java) // if error happened here remove 'this' and add requireActivity()
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        viewDataBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_sample, container, false)
        viewDataBinding.lifecycleOwner = this.viewLifecycleOwner
        setHasOptionsMenu(true)

//        viewDataBinding.sampleSpinner.adapter=
        viewDataBinding.sampleQuantityEdt.filters =
            arrayOf<InputFilter>(InputFilterMinMax(1, 15))
        viewModel.getAllMedicine()
        viewModel.medicines.observe(viewLifecycleOwner,
            Observer { medicineList ->
                val arrayAdapter = ArrayAdapter(
                    requireContext(),
                    android.R.layout.simple_spinner_item,
                    medicineList!!
                )
                arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                viewDataBinding.sampleSpinner.adapter = arrayAdapter
            })

        viewDataBinding.submitSampleBtn.setOnClickListener {
            val quantity = viewDataBinding.sampleQuantityEdt.text.toString()

            when {
                quantity.isEmpty() -> {
                    viewDataBinding.sampleQuantityEdt.error = "Enter Quantity"
                    viewDataBinding.sampleQuantityEdt.requestFocus()
                    return@setOnClickListener
                }
            }
            viewModel.submitSample(
                Sample(
                    null,
                    null,
                    FirebaseAuth.getInstance().uid!!,
                    viewDataBinding.sampleSpinner.selectedItem as String,
                    quantity.toInt()
                )
            )
        }

        viewDataBinding.sampleRecycler.setHasFixedSize(true)
        viewDataBinding.sampleRecycler.adapter=sampleAdapter
        viewModel.samples.observe(viewLifecycleOwner,
            Observer { samples ->
                sampleList.clear()
                sampleList.addAll(samples!!)
                sampleAdapter.notifyDataSetChanged()
            })
        viewModel.getAllSample()


        return viewDataBinding.root
    }


}