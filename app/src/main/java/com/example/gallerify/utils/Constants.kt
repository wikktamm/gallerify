package com.example.gallerify.utils

object Constants {
    const val REQUEST_CODE_GALLERY = 144
    const val REQUEST_CODE_CAMERA = 145
    const val REQUEST_CODE_GOOGLE_SIGN_IN = 147

    //firebase data
    const val FIRESTORE_COLLECTION_IMAGES = "images"
    const val FIRESTORE_COLLECTION_USERS_IMAGES = "img"

    //logging utils
    const val TAG_LoginActivity = "LoginActivity"
    const val TAG_ImageUtil = "IMAGE_UTIL"

    // min confidence value for the tags to be accepted
    const val LABEL_CONFIDENCE_MIN_VALUE = 0.7

    // patterns
    const val TIME_PATTERN_IMAGEFILE_TIMESTAMP = "yyyyMMdd_HHmmss"
}