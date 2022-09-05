package com.example.noteapp.ui.auth

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.noteapp.R
import com.example.noteapp.databinding.FragmentLoginBinding
import com.example.noteapp.util.*
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginFragment : Fragment() {


    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding
    private val viewModel: AuthViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observer()
        binding!!.singUpLayout.setOnClickListener{
            findNavController().navigate(R.id.action_loginFragment_to_registrationFragment)
        }
        binding!!.LoginBtnLoginAct.setOnClickListener {
            if (isValid()) {
                viewModel.login(
                    email = binding!!.emailTILog.editText!!.text.toString(),
                    password = binding!!.passwordTILog.editText!!.text.toString()
                )
            }
        }

    }

    private fun observer() {
        viewModel.login.observe(viewLifecycleOwner) { state ->
            when (state) {
                is Resource.Loading -> {
                    binding!!.LoginBtnLoginAct.text=""
                    binding!!.loginProgressBar.show()
                }
                is Resource.Error -> {
                    binding!!.LoginBtnLoginAct.text = "Login"
                    binding!!.loginProgressBar.hide()
                    activity?.showToast(state.message.toString())

                }
                is Resource.Success -> {
                    binding!!.LoginBtnLoginAct.text="Login"
                    binding!!.loginProgressBar.hide()
                    activity?.showToast(state.data.toString())
                    findNavController().navigate(R.id.action_loginFragment_to_homeFragment)

                }
            }
        }
    }

    private fun isValid(): Boolean {
        var isValid = true

        if (binding!!.emailTILog.editText!!.text.isNullOrEmpty()) {
            isValid = false
            activity?.showToast(getString(R.string.enter_email))
        } else {
            if (!binding!!.emailTILog.editText!!.text.toString().isValidEmail()) {
                isValid = false
                activity?.showToast(getString(R.string.invalid_email))
            }
        }
        if (binding!!.passwordTILog.editText!!.text.isNullOrEmpty()) {
            isValid = false
            activity?.showToast(getString(R.string.enter_password))
        } else {
            if (binding!!.passwordTILog.editText!!.text.toString().length < 8) {
                isValid = false
                activity?.showToast(getString(R.string.invalid_password))
            }
        }
        return isValid
    }

    override fun onStart() {
        super.onStart()
        viewModel.getSession { user ->
            if(user != null) {
                findNavController().navigate(R.id.action_loginFragment_to_homeFragment)
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}