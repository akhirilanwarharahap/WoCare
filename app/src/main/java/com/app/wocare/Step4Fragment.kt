package com.app.wocare

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import android.widget.TextView

class Step4Fragment : Fragment() {
    // TODO: Rename and change types of parameters
    lateinit var btnNext: RelativeLayout
    lateinit var pertanyaan: TextView
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val v = inflater.inflate(R.layout.fragment_step4, container, false)

        // value id
        btnNext = v.findViewById(R.id.btnNext)
        pertanyaan = v.findViewById(R.id.tvPertanyaan)

        insertData()

        btnNext.setOnClickListener {
            val i = Intent(activity, HomeActivity::class.java)
            activity?.startActivity(i)
            activity?.finish()
        }
        return v
    }
    private fun insertData() {
        val dataPertanyaan = "How do you weight?"

        pertanyaan.text = dataPertanyaan
    }
}