package com.app.wocare.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.app.wocare.R
import com.app.wocare.models.ChatModel
import com.app.wocare.models.ResponseChat

class ChatAdapter(private var listChat: MutableList<ChatModel>): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val BOT_MESSAGE = 0
    private val USER_MESSAGE = 1

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return when(viewType){
            BOT_MESSAGE -> {
                val v = inflater.inflate(R.layout.layout_chat, parent, false)
                BotViewHolder(v)
            }

            USER_MESSAGE -> {
                val v = inflater.inflate(R.layout.layout_chat_user, parent, false)
                MyViewHolder(v)
            }

            else -> {
                val v = inflater.inflate(R.layout.layout_chat, parent, false)
                BotViewHolder(v)
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (listChat[position].sender){
            "bot"  -> 0
            "user" -> 1
            else   -> -1
        }
    }

    override fun getItemCount(): Int = listChat.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val chatModel = listChat[position]
        when (chatModel.sender){
            "bot" -> {
                holder as BotViewHolder
                holder.messageText.text = chatModel.message
            }
            "user" -> {
                holder as MyViewHolder
                holder.messageText.text = chatModel.message
            }
        }
    }

    class MyViewHolder(v: View): RecyclerView.ViewHolder(v) {
        val messageText: TextView = v.findViewById(R.id.userChat)
    }

    class BotViewHolder(v: View): RecyclerView.ViewHolder(v) {
        val messageText: TextView = v.findViewById(R.id.botChat)
    }
}