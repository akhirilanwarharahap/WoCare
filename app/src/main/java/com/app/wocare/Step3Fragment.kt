package com.app.wocare

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.RelativeLayout
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.FragmentTransaction

class Step3Fragment : Fragment() {
    // TODO: Rename and change types of parameters
    lateinit var btnNext: RelativeLayout
    private lateinit var pertanyaan: TextView
    private lateinit var height: EditText

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val v = inflater.inflate(R.layout.fragment_step3, container, false)

        // value id
        btnNext = v.findViewById(R.id.btnNext)
        pertanyaan = v.findViewById(R.id.tvPertanyaan)
        height = v.findViewById(R.id.edHeight)
        insertData()

//        val data = this.arguments
//        if (data != null){
//            val dataTanggal = data.getString("dataTanggal")
//            val dataBulan = data.getString("dataBulan")
//            val dataTahun = data.getString("dataTahun")
//            val dataUsername = data.getString("dataUsername")
//        }

        btnNext.setOnClickListener{

//            val dataHeight = height.text.toString()
//
//            if (dataHeight.isEmpty()){
//                errorMessage()
//            } else {
//
//
//
//            }
            switchFragment()
        }
        return v
    }

    private fun errorMessage() {
        Toast.makeText(requireContext(), "Tinggi belum di isi...", Toast.LENGTH_SHORT).show()
    }

    private fun switchFragment() {
        val frag = Step4Fragment()
        val trans: FragmentTransaction = parentFragmentManager.beginTransaction()
        trans.replace(R.id.frame, frag)
        trans.disallowAddToBackStack()
        trans.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
        trans.commit()
    }

    private fun insertData() {
        val dataPertanyaan = "How tall are you?"

        pertanyaan.text = dataPertanyaan
    }
}