package com.app.wocare.homeFragments

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.app.wocare.R
import com.app.wocare.adapters.ChatAdapter
import com.app.wocare.api.chatbotApi
import com.app.wocare.models.ChatModel
import com.app.wocare.models.RequestChat
import com.app.wocare.models.ResponseChat
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class WoAskFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private lateinit var columnChat: EditText
    private lateinit var btnSend: ImageView
    private lateinit var rv: RecyclerView
    private lateinit var chatAdapter: ChatAdapter
    private var chatList: MutableList<ChatModel> = ArrayList()
    private val BOT_KEY: String = "bot"
    private val USER_KEY: String = "user"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val v = inflater.inflate(R.layout.fragment_wo_ask, container, false)

        columnChat = v.findViewById(R.id.edMsg)
        btnSend = v.findViewById(R.id.btnSend)
        rv = v.findViewById(R.id.rv)

        chatList = ArrayList()
        chatAdapter = ChatAdapter(chatList)
        val lm = LinearLayoutManager(requireContext())
        rv.layoutManager = lm
        rv.adapter = chatAdapter

        btnSend.setOnClickListener {
            val dataChat = columnChat.text.toString()
            if (dataChat.isEmpty()){
                Toast.makeText(requireContext(), "Please enter your message...", Toast.LENGTH_SHORT).show()
            } else {
                getResponse(dataChat)
                columnChat.text.clear()
            }
        }
        return v
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun getResponse(dataChat: String) {
        chatList.add(ChatModel(dataChat, USER_KEY))
        chatAdapter.notifyDataSetChanged()
        println("User Input = $dataChat")
        val inputData = RequestChat(dataChat)
        val callApi = chatbotApi().getService().postChat(inputData)

        callApi.enqueue(object : Callback<ResponseChat> {
            override fun onResponse(call: Call<ResponseChat>, response: Response<ResponseChat>) {
                if (response.isSuccessful){
                    val responseChat = response.body()!!.response
                    chatList.add(ChatModel(responseChat, BOT_KEY))
                    chatAdapter.notifyDataSetChanged()
                    println("jawaban = $responseChat")
                } else {
                    println("error 404")
                }
            }
            override fun onFailure(call: Call<ResponseChat>, t: Throwable) {
                chatList.add(ChatModel(t.message.toString(), BOT_KEY))
                chatAdapter.notifyDataSetChanged()
            }
        })
    }
}
