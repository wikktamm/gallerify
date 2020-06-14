package com.example.gallerify

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
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
}