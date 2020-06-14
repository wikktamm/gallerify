package com.example.gallerify

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import kotlinx.android.synthetic.main.dialog_add_picture.*


class DialogAddPicture : DialogFragment() {
    private var _onOptionCameraClickListener: (() -> Unit)? = null
    fun setOnOptionCameraClickListener(func: (() -> Unit)) {
        _onOptionCameraClickListener = func
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.dialog_add_picture, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        btCamera.setOnClickListener {
            _onOptionCameraClickListener?.invoke()
        }
    }
}