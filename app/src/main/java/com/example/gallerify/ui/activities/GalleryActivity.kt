package com.example.gallerify.ui.activities

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.FileProvider
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.gallerify.R
import com.example.gallerify.adapters.ImageAdapter
import com.example.gallerify.repositories.ImageRepository
import com.example.gallerify.ui.dialogs.DialogAddPicture
import com.example.gallerify.ui.dialogs.DialogNewPicture
import com.example.gallerify.utils.Constants.REQUEST_CODE_CAMERA
import com.example.gallerify.utils.Constants.REQUEST_CODE_GALLERY
import com.example.gallerify.utils.Constants.TIME_PATTERN_IMAGEFILE_TIMESTAMP
import com.example.gallerify.utils.displayToast
import com.example.gallerify.utils.resources.Resource
import com.example.gallerify.utils.resources.UserResource
import com.example.gallerify.viewmodels.GalleryViewModel
import com.example.gallerify.viewmodels.GalleryViewModelFactory
import kotlinx.android.synthetic.main.activity_gallery.*
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*
import kotlin.reflect.typeOf

class GalleryActivity : AppCompatActivity() {

    lateinit var viewModel: GalleryViewModel
    lateinit var adapter: ImageAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_gallery)
        setSupportActionBar(toolbar)
        val repo = ImageRepository()
        viewModel =
            ViewModelProvider(this, GalleryViewModelFactory(repo)).get(GalleryViewModel::class.java)
        setListeners()
        setObservers()
        ensureUserLoggedIn()
        setAdapter()
        viewModel.getAllImages()
    }

    private fun setAdapter() {
        val currentUser = viewModel.getCurrentUser() as UserResource.LoggedIn
        adapter = ImageAdapter(this, currentUser.uid)
        rvImages.let {
            it.adapter = adapter
            it.layoutManager = LinearLayoutManager(this)
        }
        adapter.setOnImageLoadedCallback{
            hideProgressBar()
        }
    }

    private fun setObservers() {
        viewModel.currentUser.observe(this, androidx.lifecycle.Observer { resource ->
            when (resource) {
                is UserResource.LoggedOut -> {
                    startActivity(LoginActivity.getLaunchIntent(this))
                }
            }
        })
        viewModel.images.observe(this, androidx.lifecycle.Observer { resource ->
            when (resource) {
                is Resource.Success -> {
                    //progressBar will be hidden after the adapter will load the images
                    resource.data?.let {
                        adapter.diffutil.submitList(it)
                    }
                }
                is Resource.Error -> {
                    hideProgressBar()
                    resource.message?.let {
                        displayToast(it)
                    }
                }
                is Resource.Loading -> {
                    showProgressBar()
                }
            }
        })
    }

    private fun showProgressBar() {
        progressBar.visibility = View.VISIBLE
    }

    private fun hideProgressBar() {
        progressBar.visibility = View.INVISIBLE
    }

    private fun ensureUserLoggedIn() {
        if (viewModel.getCurrentUser() == UserResource.LoggedOut()) {
            startActivity(LoginActivity.getLaunchIntent(this))
        } else {
            viewModel.setUserAsLoggedIn()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.my_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_logout -> {
                viewModel.logout()
                true
            }
            else -> false
        }
    }

    private fun setListeners() {
        fab.setOnClickListener {
            showDialogChoosePictureFrom()
        }

    }

    private fun showDialogChoosePictureFrom() {
        val dialog = DialogAddPicture()
        dialog.setOnOptionGalleryClickListener {
            openGallery()
            dialog.dismiss()
        }
        dialog.setOnOptionCameraClickListener {
            openCamera()
            dialog.dismiss()
        }
        dialog.show(supportFragmentManager, "dialog1")
    }

    private fun openGallery() {
        val intent = Intent(Intent.ACTION_GET_CONTENT)
        intent.type = "image/*"
        startActivityForResult(intent, REQUEST_CODE_GALLERY)
    }

    private fun openCamera() {
        val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(packageManager) != null) {
            var photoFile: File? = null
            try {
                photoFile = createImageFile()
            } catch (e: IOException) {
                return
            }
            // Continue only if the File was successfully created
            val photoURI = FileProvider.getUriForFile(
                this,
                "com.example.gallerify" +
                        ".fileprovider",
                photoFile
            )
            takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
            startActivityForResult(takePictureIntent, REQUEST_CODE_CAMERA)
        }
    }

    private lateinit var currentPhotoPath: String

    @SuppressLint("SimpleDateFormat")
    @Throws(IOException::class)
    private fun createImageFile(): File {
        val timeStamp: String = SimpleDateFormat(TIME_PATTERN_IMAGEFILE_TIMESTAMP).format(Date())
        val storageDir: File? =
            getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        return File.createTempFile(
            "JPEG_${timeStamp}_", /* prefix */
            ".jpg", /* suffix */
            storageDir /* directory */
        ).apply {
            currentPhotoPath = absolutePath
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CODE_GALLERY && resultCode == Activity.RESULT_OK) {
            data?.data?.let {
                displayDialogWithNewPicture(it)
            }
        } else if (requestCode == REQUEST_CODE_CAMERA && resultCode == Activity.RESULT_OK) {
            val file = File(currentPhotoPath)
            val bitmap = MediaStore.Images.Media
                .getBitmap(contentResolver, Uri.fromFile(file)) //todo
            displayDialogWithNewPicture(bitmap)
        }
    }

    private fun displayDialogWithNewPicture(uri: Uri) {
        val dialog = DialogNewPicture()
        dialog.setOnOptionYesClickListener {
            //todo
        }
        dialog.rememberUriToDisplay(uri)
        dialog.show(supportFragmentManager, "dialog2")
    }

    private fun displayDialogWithNewPicture(bitmap: Bitmap) {
        val dialog = DialogNewPicture()
        dialog.setOnOptionYesClickListener {
            //todo
        }
        dialog.rememberBitmapToDisplay(bitmap)
        dialog.show(supportFragmentManager, "dialog2")
    }
}