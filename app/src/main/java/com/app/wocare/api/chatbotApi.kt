package com.app.wocare.api

import com.app.wocare.models.RequestChat
import com.app.wocare.models.ResponseChat
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

class chatbotApi {
    private val url = "192.168.1.7:5000"
    private val urlBase = "http://$url/"

    private fun getRetrofit(): Retrofit {

        return Retrofit.Builder()
            .baseUrl(urlBase)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    fun getService(): chatbot = getRetrofit().create(chatbot::class.java)
}

interface chatbot {
    @POST("chat")
    fun postChat(@Body requestChat: RequestChat): Call<ResponseChat>
}
