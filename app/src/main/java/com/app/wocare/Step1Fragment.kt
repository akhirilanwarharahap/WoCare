package com.app.wocare

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.FragmentTransaction

class Step1Fragment : Fragment() {
    // TODO: Rename and change types of parameters
    lateinit var btnNext: RelativeLayout
    private lateinit var pertanyaan: TextView
    private lateinit var informasi: TextView
    private lateinit var username: TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val v = inflater.inflate(R.layout.fragment_step1, container, false)

        // value id
        btnNext = v.findViewById(R.id.btnNext)
        pertanyaan = v.findViewById(R.id.tvPertanyaan)
        informasi = v.findViewById(R.id.tvIsi)
        username = v.findViewById(R.id.edUsername)
        insertData()

        btnNext.setOnClickListener{
//            val dataUsername = username.text.toString()
//
//            if (dataUsername.isEmpty()){
//                errorMessage()
//            } else {
//                saveDatatoPrefences(dataUsername)
//
//            }
            switchFragment()
        }
        return v
    }

    private fun saveDatatoPrefences(dataUsername: String) {
        val sharedData = requireContext().getSharedPreferences("dataUsername", Context.MODE_PRIVATE)
        val editor = sharedData.edit()
        editor.putString("username", dataUsername)
        editor.apply()
    }

    private fun errorMessage() {
        Toast.makeText(requireContext(), "Username belum di isi...", Toast.LENGTH_SHORT).show()
    }

    private fun switchFragment() {
        val frag = Step2Fragment()
        val trans: FragmentTransaction = parentFragmentManager.beginTransaction()
        trans.replace(R.id.frame, frag)
        trans.disallowAddToBackStack()
        trans.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
        trans.commit()
    }

    private fun insertData() {
        val dataPertanyaan = "Enter your chosen username"
        val dataInformasi = "Kindly input the username you'd like to use"

        pertanyaan.text = dataPertanyaan
        informasi.text = dataInformasi
    }
}