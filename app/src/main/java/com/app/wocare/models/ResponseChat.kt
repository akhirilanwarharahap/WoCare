package com.app.wocare.models

import kotlinx.parcelize.Parcelize
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

@Parcelize
data class ResponseChat (

	@field:SerializedName("response")
	val response: String? = null

) : Parcelable
