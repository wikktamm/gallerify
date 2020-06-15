package com.example.gallerify.ui.activities

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.FileProvider
import androidx.core.net.toUri
import com.example.gallerify.R
import com.example.gallerify.ui.dialogs.DialogAddPicture
import com.example.gallerify.ui.dialogs.DialogNewPicture
import com.example.gallerify.utils.Constants
import com.example.gallerify.utils.Constants.REQUEST_CODE_CAMERA
import com.example.gallerify.utils.Constants.REQUEST_CODE_GALLERY
import kotlinx.android.synthetic.main.activity_main.*
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setListeners()
//        imageView.buildDrawingCache(true)
        // To use default options:
//        val labeler = ImageLabeling.getClient(ImageLabelerOptions.DEFAULT_OPTIONS)
//
//        button.setOnClickListener {
//
//            val drawable = imageView.drawable as BitmapDrawable
//            val bitmap: Bitmap = drawable.bitmap
//            val image = InputImage.fromBitmap(bitmap, 0)
//            labeler.process(image)
//                .addOnSuccessListener { labels ->
//                    // Task completed successfully
//                    // ...
//                    for (label in labels) {
//                        val text = label.text
//
//                        textView.append(text + "\n")
//                    }
//                }
//                .addOnFailureListener { e ->
//                    // Task failed with an exception
//                    // ...
//                }
//        }


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
        }
        dialog.setOnOptionUrlClickListener {
            //todo
        }
        dialog.show(supportFragmentManager, "dialog1")
    }

    private fun openGallery() {
        val intent = Intent(Intent.ACTION_GET_CONTENT)
        intent.type = "image/*"
        startActivityForResult(intent, Constants.REQUEST_CODE_GALLERY)
    }

    private fun openCamera() {
        val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(packageManager) != null) {
            // Create the File where the photo should go
            var photoFile: File? = null
            try {
                photoFile = createImageFile()
            } catch (e: IOException) {

            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
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

    }

    private lateinit var currentPhotoPath: String

    @SuppressLint("SimpleDateFormat")
    @Throws(IOException::class)
    private fun createImageFile(): File {
        val timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
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
                .getBitmap(contentResolver, Uri.fromFile(file))
            displayDialogWithNewPicture(bitmap)
        }
    }

    private fun displayDialogWithNewPicture(uri: Uri) {
        val dialog2 = DialogNewPicture()
        dialog2.setOnOptionYesClickListener {
            //todo
        }
        dialog2.rememberUriToDisplay(uri)
        dialog2.show(supportFragmentManager, "dialog2")
    }

    private fun displayDialogWithNewPicture(bitmap: Bitmap) {
        val dialog2 = DialogNewPicture()
        dialog2.setOnOptionYesClickListener {
            //todo
        }
        dialog2.rememberBitmapToDisplay(bitmap)
        dialog2.show(supportFragmentManager, "dialog2")
    }


}