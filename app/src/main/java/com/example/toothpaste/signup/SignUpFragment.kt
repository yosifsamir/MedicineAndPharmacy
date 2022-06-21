package com.example.toothpaste.signup

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.toothpaste.R
import com.example.toothpaste.databinding.FragmentSignUpBinding
import com.example.toothpaste.ui.login.LoginViewModel
import com.example.toothpaste.utils.Event


class SignUpFragment : Fragment() {

    private lateinit var viewModel: LoginViewModel
    private lateinit var viewDataBinding: FragmentSignUpBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        viewDataBinding= DataBindingUtil.inflate(inflater, R.layout.fragment_sign_up, container, false)
        viewDataBinding.lifecycleOwner = this.viewLifecycleOwner
        viewModel = ViewModelProvider.AndroidViewModelFactory.getInstance(requireActivity().application).create(LoginViewModel::class.java)
        setHasOptionsMenu(true)

        subscribeToObservers()

        viewDataBinding.textViewHaveAcc.setOnClickListener {
            findNavController().navigate(R.id.action_signUpFragment_to_signInFragment)
        }


        viewDataBinding.buttonSignUp.setOnClickListener {
            //findNavController().navigate(R.id.action_signUpFragment_to_signInFragment)
            viewModel.registerUser(
                viewDataBinding.editTextTextEmail.editText?.text.toString(),
                viewDataBinding.editTextTextFullName.editText?.text.toString(),
                viewDataBinding.editTextTextRegNo.editText?.text.toString(),
                viewDataBinding.editTextTextPass.editText?.text.toString(),
                viewDataBinding.editTextPhoneNumber.editText?.text.toString()
            )
        }


        return viewDataBinding.root
    }

    private fun subscribeToObservers() {

        viewModel.registerStatus.observe(viewLifecycleOwner, object : Observer<Event> {
            override fun onChanged(event: Event?) {
                if (event==Event.ERROR){
                    viewDataBinding.registerProgressbar.isVisible = false
                    //use Toast or Snackbar
                    Toast.makeText(context,"Something Wrong",Toast.LENGTH_LONG).show()
                }
                else if (event==Event.LOADING){
                    viewDataBinding.registerProgressbar.isVisible = true
                }else{
                    viewDataBinding.registerProgressbar.isVisible = false
//                    use snackbar or toast here like this :-
//                    showSnackbar("Registration successful")
                }
            }
        })
    }


}