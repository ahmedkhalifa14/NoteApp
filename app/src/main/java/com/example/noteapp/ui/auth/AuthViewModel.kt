package com.example.noteapp.ui.auth

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.noteapp.model.User
import com.example.noteapp.repo.AuthRepository
import com.example.noteapp.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
     val repository: AuthRepository
) : ViewModel() {
//Register
private val _register = MutableLiveData<Resource<String>>()
    val register: LiveData<Resource<String>>
        get() = _register
    fun register(
        email: String,
        password: String,
        user: User
    ) {
        _register.value = Resource.Loading()
        repository.registerUser(
            email = email,
            password = password,
            user = user
        ) { _register.value = it }
    }

    //Login
    private val _login = MutableLiveData<Resource<String>>()
    val login: LiveData<Resource<String>>
        get() = _login
    fun login(
        email: String,
        password: String
    ) {
        _login.value = Resource.Loading()
        repository.loginUser(
            email,
            password
        ){
            _login.value = it
        }
    }
    //Logout

    fun logout(result: () -> Unit){
        repository.logout(result)
    }
    //get user session
    fun getSession(result: (User?) -> Unit){
        repository.getSession(result)
    }


}