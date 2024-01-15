package com.app.wocare.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.app.wocare.R
import com.app.wocare.models.News
import com.squareup.picasso.Picasso

class InsightAdapter(private var data: MutableList<News>, context: Context): RecyclerView.Adapter<InsightAdapter.MyViewHolder>() {

    private val mInflater: LayoutInflater = LayoutInflater.from(context)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val v: View = mInflater.inflate(R.layout.insight_layout, parent, false)
        return MyViewHolder(v)
    }

    override fun getItemCount(): Int = data.size

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: MyViewHolder, pos: Int) {
        val listData = data[pos]

        val panjangJudul = listData.judul?.length
        val judulBerita = listData.judul
        val limitJudul = judulBerita?.substring(0,35)

        if (panjangJudul != null) {
            if (panjangJudul >= 35){
                holder.judulBerita.text = "$limitJudul..."
            } else {
                holder.judulBerita.text = judulBerita
            }
        }
        Picasso.get().load(listData.foto).fit().into(holder.fotoBerita)
    }

    @SuppressLint("NotifyDataSetChanged")
    fun clear() {
        data.clear()
        notifyDataSetChanged()
    }

    class MyViewHolder(v: View): RecyclerView.ViewHolder(v) {
        val fotoBerita: ImageView = v.findViewById(R.id.poto)
        val judulBerita: TextView = v.findViewById(R.id.judul)
    }
}