package com.app.wocare.models

import kotlinx.parcelize.Parcelize
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

@Parcelize
data class RequestChat(
	@field:SerializedName("user_input")
	val userInput: String? = null
) : Parcelable
