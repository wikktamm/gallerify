package com.example.gallerify.viewmodels

import android.graphics.Bitmap
import android.widget.ImageView
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gallerify.repositories.ImageRepository
import com.example.gallerify.utils.resources.UserResource
import kotlinx.coroutines.launch

class GalleryViewModel(private val repo: ImageRepository) : ViewModel() {

    val currentUser: MutableLiveData<UserResource> = MutableLiveData()//(UserResource.LoggedIn())

    fun saveImage(bitmap: Bitmap) {
        viewModelScope.launch {
            repo.labelAndSaveImage(bitmap)
        }
    }

    fun getCurrentUser() = repo.getCurrentUser()

    fun logout() {
        repo.logout()
        currentUser.postValue(UserResource.LoggedOut())
    }
}
