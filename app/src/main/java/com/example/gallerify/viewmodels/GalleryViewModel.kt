package com.example.gallerify.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.gallerify.repositories.ImageRepository
import com.example.gallerify.utils.UserResource

class GalleryViewModel(private val repo: ImageRepository) : ViewModel() {

    val currentUser:MutableLiveData<UserResource> = MutableLiveData()//(UserResource.LoggedIn())

    fun getCurrentUser() = repo.getCurrentUser()

    fun logout() {
        repo.logout()
        currentUser.postValue(UserResource.LoggedOut())
    }

}