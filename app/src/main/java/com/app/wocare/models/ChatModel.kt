package com.app.wocare.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ChatModel (
     val message: String? = null,
     val sender: String? = null
): Parcelable