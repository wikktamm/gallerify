package com.example.gallerify.ui.dialogs

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
        ivResult.setImageURI(chosenPictureUri)
        tvYes.setOnClickListener {
            _onOptionYesClickListener?.invoke()
        }
    }

    fun setUriToDisplay(uri: Uri) {
        chosenPictureUri = uri
    }
}