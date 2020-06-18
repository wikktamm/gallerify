package com.example.gallerify.repositories

import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.util.Log
import android.widget.ImageView
import com.example.gallerify.models.LabelledImage
import com.example.gallerify.utils.Constants.FIRESTORE_COLLECTION_IMAGES
import com.example.gallerify.utils.Constants.FIRESTORE_COLLECTION_USERS_IMAGES
import com.example.gallerify.utils.Constants.LABEL_CONFIDENCE_MIN_VALUE
import com.example.gallerify.utils.resources.Resource
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.label.ImageLabeling
import com.google.mlkit.vision.label.defaults.ImageLabelerOptions
import kotlinx.coroutines.tasks.await
import java.util.*

class ImageRepository {
    private val imageCollection = Firebase.firestore.collection(FIRESTORE_COLLECTION_IMAGES)
    private val auth by lazy { FirebaseAuth.getInstance() }
    private val storage by lazy { FirebaseStorage.getInstance() }

    suspend fun saveImage(byteArray: ByteArray, imageView: ImageView): Resource<LabelledImage> {
        val labellingResult = labelImage(imageView)
        if (labellingResult is Resource.Error) return Resource.Error()
        val imageUID = UUID.randomUUID().toString()
        val userUid = getCurrentUser()!!.uid
        storage.reference.child("${userUid}/${imageUID}.jpg").putBytes(byteArray)
            .addOnSuccessListener {
                Log.d("123", "ok")
            }
            .addOnFailureListener { e ->
                Log.d("123", e.toString())
            }
        val labelledImage = LabelledImage(imageUID, labellingResult.data!!)
        var result: Resource<LabelledImage> = Resource.Loading()
        imageCollection.document(userUid).collection(FIRESTORE_COLLECTION_USERS_IMAGES).add(labelledImage)
            .addOnCompleteListener {
                result = Resource.Success(labelledImage)
            }
            .addOnFailureListener {
                result = Resource.Error(message = it.message)
            }
            .await()
        return result
    }

    suspend fun firebaseAuthWithGoogle(idToken: String): Resource<FirebaseUser>? {
        var result: Resource<FirebaseUser>? = null
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        auth.signInWithCredential(credential)
            .addOnCompleteListener { task ->
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

    suspend fun labelImage(imageView: ImageView): Resource<List<String>> { //todo
        imageView.buildDrawingCache(true) //todo
        var errorOccurred = false
        val tagList = mutableListOf<String>()
        val labeler = ImageLabeling.getClient(ImageLabelerOptions.DEFAULT_OPTIONS)
        val drawable = imageView.drawable as BitmapDrawable
        val bitmap: Bitmap = drawable.bitmap
        val image = InputImage.fromBitmap(bitmap, 0)
        labeler.process(image)
            .addOnSuccessListener { labels ->
                tagList.addAll(labels.filter { x -> x.confidence > LABEL_CONFIDENCE_MIN_VALUE }
                    .map { x -> x.text })
            }
            .addOnFailureListener {
                errorOccurred = true
            }.await()
        Log.d("123", tagList.joinToString(" "))
        if (errorOccurred) return Resource.Error()
        return Resource.Success(tagList)
    }
}