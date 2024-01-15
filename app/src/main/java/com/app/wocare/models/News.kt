package com.app.wocare.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class News (

    val judul: String? = null,
    val isi: String? = null,
    val foto: String? = null,

): Parcelable
