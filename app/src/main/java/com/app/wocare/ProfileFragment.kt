package com.app.wocare

import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.appcompat.widget.SwitchCompat
import androidx.fragment.app.FragmentTransaction
import com.app.wocare.models.UserDetails
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.ktx.Firebase

class ProfileFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private lateinit var tombolNotification: SwitchCompat
    private lateinit var editProfile: LinearLayout
    private lateinit var namaProfile: TextView
    private lateinit var tinggiProfile: TextView
    private lateinit var beratProfile: TextView
    private lateinit var btnLogout: RelativeLayout
    private val mAuth: FirebaseAuth = Firebase.auth
    private lateinit var uid: String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val v = inflater.inflate(R.layout.fragment_profile, container, false)
        //  define id
        tombolNotification = v.findViewById(R.id.swnotif)
        editProfile = v.findViewById(R.id.edProfile)
        namaProfile = v.findViewById(R.id.nama)
        tinggiProfile = v.findViewById(R.id.tinggi)
        beratProfile = v.findViewById(R.id.berat)
        btnLogout = v.findViewById(R.id.btnSignOut)

        //  get Data current User
        val fUser = mAuth.currentUser
        uid = fUser!!.uid

        getDataProfileFromFirebase(uid)

        editProfile.setOnClickListener{
            val secondFrag = EditProfileFragment()
            val trans: FragmentTransaction = parentFragmentManager.beginTransaction()
            trans.replace(R.id.frame, secondFrag)
            trans.addToBackStack(null)
            trans.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
            trans.commit()
        }

        btnLogout.setOnClickListener {
            val dialog = Dialog(requireContext())
            dialog.setContentView(R.layout.dialog_logout)
            dialog.setCancelable(false)
            dialogFull(dialog)

            val logout = dialog.findViewById<TextView>(R.id.logout)
            val cancel = dialog.findViewById<TextView>(R.id.cancel)

            logout.setOnClickListener {
                functionLogout()
                dialog.dismiss()
            }
            cancel.setOnClickListener {
                dialog.dismiss()
            }
            dialog.show()
        }

        //  set notif defaults
        tombolNotification.isChecked = false
        tombolNotification.text = tombolNotification.textOff
        //  on/off notif
        tombolNotification.setOnClickListener {
            if (tombolNotification.isChecked){
                tombolNotification.text = tombolNotification.textOn
            } else {
                tombolNotification.text = tombolNotification.textOff
            }
        }
        return v
    }

    private fun dialogFull(d: Dialog) {
        if (d.window != null){
            d.window!!.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
            d.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        }
    }

    private fun functionLogout() {
        mAuth.signOut()
        val activity = requireActivity()
        val i = Intent(activity, LoginActivity::class.java)
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
        activity.startActivity(i)
        activity.finish()
    }

    private fun getDataProfileFromFirebase(uid: String) {
        val db = FirebaseDatabase.getInstance().getReference("Users")

        db.child(uid).addListenerForSingleValueEvent(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()){
                    val userData = snapshot.getValue(UserDetails::class.java)
                    if (userData != null){
                        val nama = userData.username
                        val tinggi = userData.tinggi
                        val berat = userData.berat

                        fetchingData(nama, tinggi, berat)
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
    }

    private fun fetchingData(nama: String?, tinggi: String?, berat: String?) {
        namaProfile.text = nama
        tinggiProfile.text = tinggi
        beratProfile.text = berat
    }
}