package com.example.gallerify.ui.activities

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.gallerify.R
import com.example.gallerify.utils.Constants.REQUEST_CODE_CAMERA
import com.example.gallerify.ui.dialogs.DialogAddPicture
import com.example.gallerify.ui.dialogs.DialogNewPicture
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.dialog_new_picture.*


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
            val intent = Intent(Intent.ACTION_GET_CONTENT)
            intent.type = "image/*"
            dialog.dismiss()
            startActivityForResult(intent, REQUEST_CODE_CAMERA)
        }

        dialog.show(supportFragmentManager, "dialog1")
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CODE_CAMERA && resultCode == Activity.RESULT_OK) {
            data?.data?.let {
                val dialog2 = DialogNewPicture()
                dialog2.setOnOptionYesClickListener { dialog2.ivResult.setImageDrawable(resources.getDrawable(R.drawable.common_google_signin_btn_icon_light)) }
                dialog2.setOnOptionNoClickListener { dialog2.dismiss() }
                dialog2.setUriToDisplay(it)
                dialog2.show(supportFragmentManager, "dialog2")

//                if(dialog2.ivResult==null) Toast.makeText(this,"aa",Toast.LENGTH_SHORT).show()
            }

        }
    }
}