package com.example.gallerify

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AlertDialogLayout
import kotlinx.android.synthetic.main.activity_main.*


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
//            AlertDialog.Builder(this)
//                .setView(R.layout.dialog_add_picture)
//                .setNegativeButton("Cancel") { dialogInterface, _ ->
//                    dialogInterface.dismiss()
//                }.setTitle("")
//                .create().show()
            val dialog = DialogAddPicture()
            dialog.setOnOptionCameraClickListener {
                Toast.makeText(this, "aaa", Toast.LENGTH_SHORT).show()
            }

            dialog.show(supportFragmentManager, "dialog1")
        }

    }
}