package com.app.wocare.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class UserDetails (

    val nama: String? = null,
    val username: String? = null,
    val email: String? = null,
    val foto: String? = null,
    val dateBirth: String? = null,
    val height: String? = null,
    val weight: String? = null

): Parcelable
