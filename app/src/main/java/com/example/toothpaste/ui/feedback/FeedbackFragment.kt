package com.example.toothpaste.ui.feedback

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.toothpaste.R
import com.example.toothpaste.adapter.FeedbackAdapter
import com.example.toothpaste.databinding.FragmentFeedbackBinding


class FeedbackFragment : Fragment() {
    private lateinit var viewModel: FeedbackViewModel
    private lateinit var viewDataBinding: FragmentFeedbackBinding
    private var feedbackListAdapter= mutableListOf<String>()

    private val feedbackAdapter:FeedbackAdapter by lazy {FeedbackAdapter(requireContext(), feedbackListAdapter)}
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this).get(FeedbackViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        viewDataBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_feedback, container, false)
        viewDataBinding.lifecycleOwner = this.viewLifecycleOwner
        setHasOptionsMenu(true)

        viewModel.loadAllFeedback()

        viewDataBinding.apply {
            feedbackRecycler.setHasFixedSize(true)
            feedbackRecycler.adapter=feedbackAdapter
            feedbackBtn.setOnClickListener {
                val feedback = feedbackEdt.text.toString()
                when {
                    feedback.isEmpty() -> {
                        feedbackEdt.error = "Enter your feedback"
                        feedbackEdt.requestFocus()
                        return@setOnClickListener
                    }
                }
                viewModel.addFeedback(feedback)
            }
        }

        viewModel.feedbacksMutableLiveData.observe(viewLifecycleOwner, object : Observer<MutableList<String>> {
            override fun onChanged(feedbackList: MutableList<String>?) {
                feedbackListAdapter.clear()
                if (feedbackList != null) {
                    feedbackListAdapter.addAll(feedbackList)
                }
                feedbackAdapter.notifyDataSetChanged()
            }
        })

        viewModel.stateLiveData.observe(viewLifecycleOwner,
            Observer<Boolean?> { state -> if (state == true) viewDataBinding.feedbackEdt.setText("") })

        return viewDataBinding.root
    }


}