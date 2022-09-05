package com.example.noteapp.ui.auth

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.noteapp.R
import com.example.noteapp.databinding.FragmentRegistrationBinding
import com.example.noteapp.model.User
import com.example.noteapp.util.*
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RegistrationFragment : Fragment() {


    private var _binding: FragmentRegistrationBinding? = null
    private val binding get() = _binding
    private val viewModel: AuthViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentRegistrationBinding.inflate(inflater, container, false)
        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observer()
        binding!!.singUpBtn.setOnClickListener {
            if (valid()){
                viewModel.register(
                    email = binding?.emailTIReg!!.editText!!.text.toString(),
                    password = binding?.passwordTIReg!!.editText!!.text.toString(),
                    user = getUserData()
                )
            }
        }
    }

    private fun observer() {
        viewModel.register.observe(viewLifecycleOwner) { state ->
            when(state){
                is Resource.Loading -> {
                    binding!!.singUpBtn.text = ""
                    binding!!.registerProgressBar.show()
                }
                is Resource.Error -> {
                    binding!!.singUpBtn.text = "Sing Up"
                    binding!!.registerProgressBar.hide()
                    activity?.showToast(state.message.toString())
                    Log.e("error",state.message.toString())
                }
                is Resource.Success -> {
                    binding!!.singUpBtn.text = "Sign Up"
                    binding!!.registerProgressBar.hide()
                    activity?.showToast(state.data.toString())
                  findNavController().navigate(R.id.action_registrationFragment_to_loginFragment)
                }
            }
        }
    }

    private fun getUserData(): User {
        return User(
            id = "",
            first_name = binding!!.firstNameTIReg.editText!!.text.toString(),
            last_name = binding!!.lastNameTIReg.editText!!.text.toString(),
            email = binding!!.emailTIReg.editText!!.text.toString(),
        )
    }

    private fun valid(): Boolean {
        var isValid = true

        if (binding!!.firstNameTIReg.editText!!.text.isNullOrEmpty()){
            isValid = false
            activity?.showToast(getString(R.string.enter_first_name))
        }

        if (binding!!.lastNameTIReg.editText!!.text.isNullOrEmpty()){
            isValid = false
            activity?.showToast(getString(R.string.enter_last_name))
        }


        if (binding!!.emailTIReg.editText!!.text.isNullOrEmpty()){
            isValid = false

            activity?.showToast(getString(R.string.enter_email))
        }else{
            if (!binding!!.emailTIReg.editText!!.text.toString().isValidEmail()){
                isValid = false
                activity?.showToast(getString(R.string.invalid_email))
            }
        }
        if (binding!!.passwordTIReg.editText!!.text.isNullOrEmpty()){
            isValid = false
            activity?.showToast(getString(R.string.enter_password))
        }else{
            if (binding!!.passwordTIReg.editText!!.text.toString().length < 8){
                isValid = false
                activity?.showToast(getString(R.string.invalid_password))
            }
        }
        return isValid
    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}