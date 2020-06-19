package com.example.gallerify.models

import org.threeten.bp.LocalDateTime

data class LabelledImage(val uid: String?, val tags : MutableList<String>?)//, val whenAdded: LocalDateTime?)
{
    constructor():this(null, null)//, null)
}