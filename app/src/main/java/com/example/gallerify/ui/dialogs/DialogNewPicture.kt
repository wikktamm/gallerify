package com.example.gallerify.ui.dialogs

import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import coil.api.load
import com.example.gallerify.R
import com.example.gallerify.ui.activities.GalleryActivity
import com.example.gallerify.utils.ImageUtils
import kotlinx.android.synthetic.main.dialog_new_picture.*

class DialogNewPicture : DialogFragment() {

    private var chosenPictureUri: Uri? = null
    private var chosenPictureBitmap: Bitmap? = null

    private var _onOptionYesClickListener: (() -> Unit)? = null
    fun setOnOptionYesClickListener(func: (() -> Unit)) {
        _onOptionYesClickListener = func
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
        chosenPictureUri?.let {
            ivResult.setImageURI(chosenPictureUri)
        }
        tvYes.setOnClickListener {
            val viewModel = (activity as GalleryActivity).viewModel
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O) {
                val bitmap = ImageUtils.getBitmap(ivResult)
                viewModel.saveImage(bitmap, ivResult)
            }
            else{
//                val bitmap =  ImageUtils.getBitmap(ivResult, requireActivity(), viewModel.sa)
            }
            Toast.makeText(activity, "aaa", Toast.LENGTH_SHORT).show()
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