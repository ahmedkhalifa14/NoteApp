package com.example.noteapp.repo

import com.example.noteapp.model.User
import com.example.noteapp.util.Resource

interface AuthRepository {
    fun registerUser(email: String, password: String, user: User, result: (Resource<String>) -> Unit)
    fun updateUserInfo(user: User, result: (Resource<String>) -> Unit)
    fun loginUser(email: String, password: String, result: (Resource<String>) -> Unit)
    fun forgotPassword(email: String, result: (Resource<String>) -> Unit)
    fun logout(result: () -> Unit)
    fun storeSession(id: String, result: (User?) -> Unit)
    fun getSession(result: (User?) -> Unit)
}