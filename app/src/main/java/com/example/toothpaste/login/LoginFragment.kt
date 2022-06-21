package com.example.toothpaste.ui.login

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.toothpaste.R
import com.example.toothpaste.databinding.LoginFragmentBinding
import com.example.toothpaste.utils.Event

class LoginFragment : Fragment() {

    companion object {
        fun newInstance() = LoginFragment()
    }

    private lateinit var viewModel: LoginViewModel
    private lateinit var viewDataBinding: LoginFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewDataBinding= DataBindingUtil.inflate(inflater, R.layout.login_fragment, container, false)
        viewDataBinding.lifecycleOwner = this.viewLifecycleOwner
        viewModel = ViewModelProvider.AndroidViewModelFactory.getInstance(requireActivity().application).create(LoginViewModel::class.java)
        setHasOptionsMenu(true)
        subscribeToObserver()

        viewDataBinding.siteTxt.setOnClickListener {
            val url = "https://www.gskhealthpartner.com/en-eg/registration/?fbclid=IwAR1iNgFfDxQI5bRvYkMw6LizPugNmZw9mhCjSS9FBFkUgRhB3g0IrrlWbIo"
            val i = Intent(Intent.ACTION_VIEW)
            i.data = Uri.parse(url)
            startActivity(i)
        }
        viewDataBinding.loginButton.setOnClickListener {
            viewModel.loginUser(
                viewDataBinding.editTextEmail.text.toString(),
                viewDataBinding.editTextPassword.text.toString()
            )


        }
        viewDataBinding.textViewDontHaveAcc.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_signUpFragment)
        }

//        viewDataBinding.textViewDontHaveAcc.setOnClickListener {
//            findNavController().navigate(R.id.action_signInFragment_to_signUpFragment)
//        }
//
//        viewDataBinding.textViewForgotPassword.setOnClickListener {
//            val passwordResetFragment = PasswordResetFragment()
//            passwordResetFragment.show(childFragmentManager, "Forgot Pass Dialog")
//        }
        return viewDataBinding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
//        setupObservers()



    }

    private fun setupObservers() {
//        viewModel.dataLoading.observe(viewLifecycleOwner,
//            EventObserver { (activity as MainActivity).showGlobalProgressBar(it) })
//
//        viewModel.snackBarText.observe(viewLifecycleOwner,
//            EventObserver { text ->
//                view?.showSnackBar(text)
//                view?.forceHideKeyboard()
//            })
//
//        viewModel.isLoggedInEvent.observe(viewLifecycleOwner, EventObserver {
//            SharedPreferencesUtil.saveUserID(requireContext(), it.uid)
//            navigateToChats()
//        })

    }


    private fun subscribeToObserver() {

        viewModel.loginStatus.observe(viewLifecycleOwner, object : Observer<Event> {
            override fun onChanged(it: Event?) {
                if (it==Event.ERROR){
                    //add Toast or Snackbar here
                    viewDataBinding.loginProgressBar.isVisible = false
                }else if (it==Event.LOADING){
                    viewDataBinding.loginProgressBar.isVisible = true

                }else{
                    viewDataBinding.loginProgressBar.isVisible = false
                    //add Toast or Snackbar here
                    Toast.makeText(context,"Logged Succeffully",Toast.LENGTH_LONG).show()
                    /*  // if you are using Activity rather Navigation components "Fragments"
                    startActivity(Intent(requireContext(), MainActivity::class.java))
                    requireActivity().finish()
                     */
//                    findNavController().navigate(R.id.action_loginFragment_to_navigation_chats)
                    findNavController().navigate(R.id.action_loginFragment_to_homeFragment)

                }
            }
        })
    }
}