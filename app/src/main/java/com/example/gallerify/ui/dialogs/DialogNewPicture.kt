package com.example.gallerify.ui.dialogs

import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.example.gallerify.R
import kotlinx.android.synthetic.main.dialog_new_picture.*

class DialogNewPicture : DialogFragment() {

    private var chosenPictureUri: Uri? = null
    private var chosenPictureBitmap: Bitmap? = null
    private var _onOptionYesClickListener: (() -> Unit)? = null
    fun setOnOptionYesClickListener(func: (() -> Unit)) {
        _onOptionYesClickListener = func
    }

    private var _onOptionNoClickListener: (() -> Unit)? = null
    fun setOnOptionNoClickListener(func: (() -> Unit)) {
        _onOptionNoClickListener = func
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.dialog_new_picture, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        chosenPictureBitmap?.let {
            ivResult.setImageBitmap(chosenPictureBitmap)
        }
        chosenPictureUri.let {
            ivResult.setImageURI(chosenPictureUri)
        }
        tvYes.setOnClickListener {
            _onOptionYesClickListener?.invoke()
        }
        tvNo.setOnClickListener {
            dismiss()
        }
        btRotateLeft.setOnClickListener {
            ivResult.rotation = ivResult.rotation - 90f
        }
        btRotateRight.setOnClickListener {
            ivResult.rotation = ivResult.rotation + 90f
        }
    }

    fun rememberUriToDisplay(uri: Uri) {
        chosenPictureUri = uri
        chosenPictureBitmap = null
    }
    fun rememberBitmapToDisplay(bitmap: Bitmap) {
        chosenPictureBitmap = bitmap
        chosenPictureUri = null
    }

}