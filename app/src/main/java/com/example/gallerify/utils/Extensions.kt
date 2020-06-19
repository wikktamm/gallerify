package com.example.gallerify.utils

import android.app.Activity
import android.widget.Toast

fun Activity.displayToast(stringId : Int){
    Toast.makeText(this, getString(stringId), Toast.LENGTH_SHORT).show()
}

fun Activity.displayToast(str : String){
    Toast.makeText(this, str, Toast.LENGTH_SHORT).show()
}