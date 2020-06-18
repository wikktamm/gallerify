package com.example.gallerify.repositories

import android.media.Image
import com.example.gallerify.utils.Constants.FIRESTORE_COLLECTION_IMAGES
import com.example.gallerify.utils.Resource
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await

class ImageRepository {
    private val imageCollection = Firebase.firestore.collection(FIRESTORE_COLLECTION_IMAGES)
    private val auth by lazy { FirebaseAuth.getInstance() }

    suspend fun saveImage(image: Image) {
        imageCollection.add(image).await()
    }

    suspend fun firebaseAuthWithGoogle(idToken: String): Resource<FirebaseUser>? {
        var result: Resource<FirebaseUser>? = null
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        auth.signInWithCredential(credential)
            .addOnCompleteListener{ task ->
                result = if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    val user = auth.currentUser
                    Resource.Success(user)
                } else {
                    // If sign in fails, display a message to the user.
                    Resource.Error()
                }
            }.await()
        return result
    }
    fun getCurrentUser() = auth.currentUser

    fun logout() = auth.signOut()
}