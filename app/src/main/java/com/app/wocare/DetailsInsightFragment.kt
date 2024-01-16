package com.app.wocare

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.text.HtmlCompat
import com.google.android.material.imageview.ShapeableImageView
import com.squareup.picasso.Picasso

class DetailsInsightFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private lateinit var judulBerita: TextView
    private lateinit var isiBerita: TextView
    private lateinit var fotoBerita: ShapeableImageView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val v = inflater.inflate(R.layout.fragment_details_insight, container, false)

        judulBerita = v.findViewById(R.id.judul)
        isiBerita = v.findViewById(R.id.isiBerita)
        fotoBerita = v.findViewById(R.id.gambar)

        val bundle = this.arguments
        fetchingData(bundle)

        return v
    }

    private fun fetchingData(bundle: Bundle?) {
        if (bundle != null){
            val datajudulBerita = bundle.getString("judulBerita")
            val dataisiBerita = bundle.getString("isiBerita")
            val datafotoBerita = bundle.getString("fotoBerita")
            judulBerita.text = datajudulBerita
            isiBerita.text = dataisiBerita?.let { HtmlCompat.fromHtml(it, HtmlCompat.FROM_HTML_MODE_LEGACY) }
            Picasso.get().load(datafotoBerita).fit().into(fotoBerita)
        }
    }
}