package com.app.wocare

import android.content.Context
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

class Step2Fragment : Fragment() {
    // TODO: Rename and change types of parameters
    lateinit var btnNext: RelativeLayout
    private lateinit var pertanyaan: TextView
    private lateinit var informasi: TextView
    private lateinit var tanggal: EditText
    private lateinit var bulan: EditText
    private lateinit var tahun: EditText
    override fun onCreateView (
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val v = inflater.inflate(R.layout.fragment_step2, container, false)

        // value id
        btnNext = v.findViewById(R.id.btnNext)
        pertanyaan = v.findViewById(R.id.tvPertanyaan)
        informasi = v.findViewById(R.id.tvIsi)
        tanggal = v.findViewById(R.id.date)
        bulan = v.findViewById(R.id.month)
        tahun = v.findViewById(R.id.year)
        insertData()

        btnNext.setOnClickListener{
            val  dataTanggal = tanggal.text.toString()
            val  dataBulan = bulan.text.toString()
            val  dataTahun = tahun.text.toString()

            if (dataTanggal.isEmpty() || dataBulan.isEmpty() || dataTahun.isEmpty()) {
                errorMessage()
            } else {
                val tglLahir = "$dataTanggal/$dataBulan/$dataTahun"
                saveDataToPreferences(tglLahir)
                switchFragment()
            }
        }
        return v
    }

    private fun saveDataToPreferences(dataTanggal: String) {
        val sharedData = requireContext().getSharedPreferences("data", Context.MODE_PRIVATE)
        val editor = sharedData.edit()
        editor.putString("tglLahir", dataTanggal)
        editor.apply()
    }

    private fun errorMessage() {
       Toast.makeText(requireContext(), "Tanggal belum di isi...", Toast.LENGTH_SHORT).show()
    }

    private fun switchFragment() {
        val frag = Step3Fragment()
        val trans: FragmentTransaction = parentFragmentManager.beginTransaction()
        trans.replace(R.id.frame, frag)
        trans.disallowAddToBackStack()
        trans.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
        trans.commit()
    }

    private fun insertData() {
        val dataPertanyaan = "When were you born?"
        val dataInformasi = "Changes in your menstrual cycle are possible as you age. Understanding this helps us make more accurate predictions"

        pertanyaan.text = dataPertanyaan
        informasi.text = dataInformasi
    }
}