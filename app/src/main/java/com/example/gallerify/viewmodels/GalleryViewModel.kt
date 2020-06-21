package com.example.gallerify.viewmodels

import android.graphics.Bitmap
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gallerify.models.LabelledImage
import com.example.gallerify.repositories.ImageRepository
import com.example.gallerify.utils.resources.Resource
import com.example.gallerify.utils.resources.UserResource
import kotlinx.coroutines.launch

class GalleryViewModel(private val repo: ImageRepository) : ViewModel() {

    val currentUser: MutableLiveData<UserResource> = MutableLiveData()//(UserResource.LoggedIn())
    val images: MutableLiveData<Resource<MutableList<LabelledImage>>> = MutableLiveData()

    fun saveImage(bitmap: Bitmap) {
        viewModelScope.launch {
            repo.labelAndSaveImage(bitmap)
        }
    }

    fun getCurrentUser(): UserResource {
        val user = repo.getCurrentUser()
        if (user != null) return UserResource.LoggedIn(user.uid)
        return UserResource.LoggedOut()
    }

    fun logout() {
        repo.logout()
        currentUser.postValue(UserResource.LoggedOut())
    }

    fun getAllImages() {
        images.postValue(Resource.Loading())
        viewModelScope.launch {
            val result = repo.getAllImages()
            images.postValue(result)
            Log.d("123", result.toString())
        }
    }

    fun setUserAsLoggedIn() {
        val userUid = repo.getCurrentUser()!!.uid
        currentUser.postValue(UserResource.LoggedIn(userUid))
    }
}
