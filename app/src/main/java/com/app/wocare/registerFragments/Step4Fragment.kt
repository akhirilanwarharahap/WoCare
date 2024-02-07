package com.app.wocare.registerFragments

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.RelativeLayout
import android.widget.TextView
import android.widget.Toast
import com.app.wocare.HomeActivity
import com.app.wocare.R
import com.app.wocare.models.UserDetails
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase

class Step4Fragment : Fragment() {
    // TODO: Rename and change types of parameters
    lateinit var btnNext: RelativeLayout
    private lateinit var pertanyaan: TextView
    private lateinit var berat: EditText
    private lateinit var mAuth: FirebaseAuth
    private lateinit var dataEmail: String
    private lateinit var dataUid: String
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val v = inflater.inflate(R.layout.fragment_step4, container, false)

        //  get Data from Firebase Auth
        mAuth = Firebase.auth
        val firebaseUser = mAuth.currentUser
        dataEmail = firebaseUser?.email.toString()
        dataUid = firebaseUser?.uid.toString()

        // value id
        btnNext = v.findViewById(R.id.btnNext)
        pertanyaan = v.findViewById(R.id.tvPertanyaan)
        berat = v.findViewById(R.id.edWeight)
        insertData()

        btnNext.setOnClickListener {
            val dataBerat = berat.text.toString()

            if (dataBerat.isEmpty()){
                errorMessage("Tinggi belum di isi...")
            } else {
                saveDataToPreferences(dataBerat)
                getdataSharedValue(dataEmail)
            }
        }
        return v
    }

    private fun getdataSharedValue(dataEmail: String?) {

        val data = context?.getSharedPreferences("data", Context.MODE_PRIVATE)

        val dataUsername = data?.getString("username", null)
        val dataLahir = data?.getString("tglLahir", null)
        val dataTinggi = data?.getString("tinggi", null)
        val dataBerat = data?.getString("berat", null)

        val newUser = UserDetails(dataUsername, dataEmail, "", dataLahir, dataTinggi, dataBerat)
        uploadDataUserToFirebase(newUser)
        println("username : $dataUsername")
        println("tanggal Lahir : $dataLahir")
        println("tinggi : $dataTinggi")
        println("berat : $dataBerat")
    }

    private fun uploadDataUserToFirebase(newUser: UserDetails) {
        val db = FirebaseDatabase.getInstance().getReference("Users")

        db.child(dataUid).setValue(newUser).addOnCompleteListener {
            if (it.isSuccessful){
                intentactivity()
            } else {
                errorMessage("Register gagal...")
            }
        }
    }

    private fun intentactivity() {
        val i = Intent(activity, HomeActivity::class.java)
        activity?.startActivity(i)
        activity?.finish()
    }

    private fun errorMessage(s: String) {
        Toast.makeText(requireContext(), s, Toast.LENGTH_SHORT).show()
    }

    private fun saveDataToPreferences(dataBerat: String) {
        val sharedData = requireContext().getSharedPreferences("data", Context.MODE_PRIVATE)
        val editor = sharedData.edit()
        editor.putString("berat", dataBerat)
        editor.apply()
    }

    private fun insertData() {
        val dataPertanyaan = "How do you weight?"

        pertanyaan.text = dataPertanyaan
    }
}