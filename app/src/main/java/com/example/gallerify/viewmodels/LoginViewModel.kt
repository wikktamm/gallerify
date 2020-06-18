package com.example.gallerify.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gallerify.repositories.ImageRepository
import com.example.gallerify.utils.resources.Resource
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.launch

class LoginViewModel(private val repo:ImageRepository) : ViewModel() {
    var currentUser: MutableLiveData<Resource<FirebaseUser>> = MutableLiveData()

    fun firebaseAuthWithGoogle(idToken: String){
        viewModelScope.launch {
            currentUser.postValue(repo.firebaseAuthWithGoogle(idToken))
        }
    }

}