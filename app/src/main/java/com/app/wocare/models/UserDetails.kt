package com.app.wocare.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class UserDetails (

    val username: String? = null,
    val email: String? = null,
    val foto: String? = null,
    val tglLahir: String? = null,
    val tinggi: String? = null,
    val berat: String? = null

): Parcelable
