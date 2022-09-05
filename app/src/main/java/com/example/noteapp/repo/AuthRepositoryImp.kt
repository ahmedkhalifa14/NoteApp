package com.example.noteapp.repo

import android.content.SharedPreferences
import com.example.noteapp.model.User
import com.example.noteapp.util.FireStoreCollection
import com.example.noteapp.util.Resource
import com.example.noteapp.util.SharedPrefConstants
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.FirebaseAuthWeakPasswordException
import com.google.firebase.firestore.FirebaseFirestore
import com.google.gson.Gson

class AuthRepositoryImp(
    val auth: FirebaseAuth,
    val database: FirebaseFirestore,
    val appPreferences: SharedPreferences,
    val gson: Gson
) : AuthRepository {
    override fun registerUser(
        email: String,
        password: String,
        user: User,
        result: (Resource<String>) -> Unit
    ) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    user.id = it.result.user?.uid ?: ""
                    updateUserInfo(user) { state ->
                        when (state) {
                            is Resource.Success -> {
                                storeSession(id = it.result.user?.uid ?: "") {
                                    if (it == null) {
                                        result.invoke(Resource.Error("User register successfully but session failed to store"))
                                    } else {
                                        result.invoke(
                                            Resource.Success("User register successfully!")
                                        )
                                    }
                                }
                            }
                            is Resource.Error -> {
                                result.invoke(Resource.Error(state.message.toString()))
                            }
                            else -> {}
                        }
                    }
                } else {
                    try {
                        throw it.exception ?: java.lang.Exception("Invalid authentication")
                    } catch (e: FirebaseAuthWeakPasswordException) {
                        result.invoke(Resource.Error("Authentication failed, Password should be at least 6 characters"))
                    } catch (e: FirebaseAuthInvalidCredentialsException) {
                        result.invoke(Resource.Error("Authentication failed, Invalid email entered"))
                    } catch (e: FirebaseAuthUserCollisionException) {
                        result.invoke(Resource.Error("Authentication failed, Email already registered."))
                    } catch (e: Exception) {
                        result.invoke(Resource.Error(e.message.toString()))
                    }
                }
            }
            .addOnFailureListener {
                result.invoke(
                    Resource.Error(
                        it.localizedMessage
                    )
                )
            }
    }


    override fun updateUserInfo(user: User, result: (Resource<String>) -> Unit) {
        val document = database.collection(FireStoreCollection.USER).document(user.id)
        document
            .set(user)
            .addOnSuccessListener {
                result.invoke(
                    Resource.Success("User has been update successfully")
                )
            }
            .addOnFailureListener {
                result.invoke(
                    Resource.Error(
                        it.localizedMessage
                    )
                )
            }
    }

    override fun loginUser(email: String, password: String, result: (Resource<String>) -> Unit) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    storeSession(id = task.result.user?.uid ?: "") {
                        if (it == null) {
                            result.invoke(Resource.Error("Failed to store local session"))
                        } else {
                            result.invoke(Resource.Success("Login successfully!"))
                        }
                    }
                }
            }.addOnFailureListener {
                result.invoke(Resource.Error("Authentication failed, Check email and password"))
            }
    }

    override fun forgotPassword(email: String, result: (Resource<String>) -> Unit) {
    }

    override fun logout(result: () -> Unit) {
        auth.signOut()
         appPreferences.edit().putString(SharedPrefConstants.USER_SESSION,null).apply()
        result.invoke()
    }

    override fun storeSession(id: String, result: (User?) -> Unit) {
        database.collection(FireStoreCollection.USER).document(id)
            .get()
            .addOnCompleteListener {
                if (it.isSuccessful){
                    val user = it.result.toObject(User::class.java)
                    appPreferences.edit().putString(SharedPrefConstants.USER_SESSION,gson.toJson(user)).apply()
                    result.invoke(user)
                }else{
                    result.invoke(null)
                }
            }
            .addOnFailureListener {
                result.invoke(null)
            }
    }

    override fun getSession(result: (User?) -> Unit) {
        val user_str = appPreferences.getString(SharedPrefConstants.USER_SESSION,null)
        if (user_str == null){
            result.invoke(null)
        }else{
            val user = gson.fromJson(user_str,User::class.java)
            result.invoke(user)
        }
    }
}