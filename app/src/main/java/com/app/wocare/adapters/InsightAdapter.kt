package com.app.wocare.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.RecyclerView
import com.app.wocare.DetailsInsightFragment
import com.app.wocare.HomeActivity
import com.app.wocare.R
import com.app.wocare.models.News
import com.squareup.picasso.Picasso
import org.apache.commons.lang3.StringUtils

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

        val fotoBerita = listData.foto
        val isiBerita = listData.isi
        val judulBerita = listData.judul
        val limitJudulBerita = StringUtils.abbreviate(judulBerita, 35)

        holder.judulBerita.text = limitJudulBerita
        Picasso.get().load(fotoBerita).fit().into(holder.fotoBerita)

        holder.itemView.setOnClickListener{

            val itemView = holder.itemView
            val bundle = Bundle()
            bundle.putString("judulBerita", judulBerita)
            bundle.putString("isiBerita", isiBerita)
            bundle.putString("fotoBerita", fotoBerita)

            switchFragment(bundle, itemView)
        }
    }

    private fun switchFragment(bundle: Bundle, itemView: View) {
        val detailFragment = DetailsInsightFragment()
        val home = itemView.context as HomeActivity

        val transaction = home.supportFragmentManager.beginTransaction()
        detailFragment.arguments = bundle
        transaction.replace(R.id.frame, detailFragment)
        transaction.addToBackStack(null)
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
        transaction.commit()
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