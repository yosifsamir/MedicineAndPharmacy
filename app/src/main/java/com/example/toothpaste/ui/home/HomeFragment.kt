package com.example.toothpaste.ui.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.findNavController
import com.example.toothpaste.R
import com.example.toothpaste.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {


    private lateinit var viewDataBinding: FragmentHomeBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        viewDataBinding= DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false)
        viewDataBinding.lifecycleOwner = this.viewLifecycleOwner
        setHasOptionsMenu(true)

        viewDataBinding.indicationCard.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_indicationFragment)
        }
        viewDataBinding.visitCalenderCard.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_visitFragment)
        }

        viewDataBinding.eventsCard.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_eventFragment)
        }

        viewDataBinding.samplesCard.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_sampleFragment)
        }

        viewDataBinding.feedbackCard.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_feedbackFragment)
        }


        return viewDataBinding.root
    }

}