package com.example.gallerify

import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.label.ImageLabeling
import com.google.mlkit.vision.label.defaults.ImageLabelerOptions
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        imageView.buildDrawingCache(true)
        // To use default options:
        val labeler = ImageLabeling.getClient(ImageLabelerOptions.DEFAULT_OPTIONS)
        
        button.setOnClickListener {

            val drawable = imageView.drawable as BitmapDrawable
            val bitmap: Bitmap = drawable.bitmap
            val image = InputImage.fromBitmap(bitmap, 0)
            labeler.process(image)
                .addOnSuccessListener { labels ->
                    // Task completed successfully
                    // ...
                    for (label in labels) {
                        val text = label.text
                        val confidence = label.confidence
                        val index = label.index
                        textView.append(text + "\n")
                    }
                }
                .addOnFailureListener { e ->
                    // Task failed with an exception
                    // ...
                }
        }



    }
}