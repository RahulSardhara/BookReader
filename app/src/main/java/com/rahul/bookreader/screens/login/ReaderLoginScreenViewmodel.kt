package com.rahul.bookreader.screens.login

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.rahul.bookreader.model.MUser
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class ReaderLoginScreenViewmodel : ViewModel() {

    private val auth = Firebase.auth

    private val _loading = MutableLiveData(false)
    val loading: MutableLiveData<Boolean> = _loading


    fun createAccount(email: String, password: String,home:()-> Unit) = viewModelScope.launch {
        _loading.value = true
        try {
            auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        val displayName = task.result.user?.email?.split('@')?.get(0)
                        createUser(displayName)
                        home.invoke()
                        _loading.value = false
                    } else {
                        _loading.value = false
                    }
                }
        } catch (e: Exception) {
            _loading.value = false
        }
    }

    private fun createUser(displayName: String?) {
        viewModelScope.launch {
            displayName?.let { name ->
                val userId = auth.currentUser?.uid
                userId?.let {
                    val user = MUser(userId = it, displayName = name, avatarUrl = "", quote = "Life is gret", profession = "android developer", id = "").toMap()
                    FirebaseFirestore.getInstance().collection("users").add(user)
                }

            }
        }
    }

    fun signIn(email: String, password: String,home:()-> Unit) = viewModelScope.launch {
        _loading.value = true
        try {
            auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        home.invoke()
                        _loading.value = false
                    } else {
                        _loading.value = false
                    }
                }
        } catch (e: Exception) {
            _loading.value = false
        }
    }


}