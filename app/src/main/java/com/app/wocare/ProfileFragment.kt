package com.app.wocare

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.appcompat.widget.SwitchCompat
import androidx.fragment.app.FragmentTransaction

class ProfileFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private lateinit var tombolNotification: SwitchCompat
    private lateinit var editProfile: LinearLayout

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val v = inflater.inflate(R.layout.fragment_profile, container, false)
        //  define id
        tombolNotification = v.findViewById(R.id.swnotif)
        editProfile = v.findViewById(R.id.edProfile)

        editProfile.setOnClickListener{
            val secondFrag = EditProfileFragment()
            val trans: FragmentTransaction = parentFragmentManager.beginTransaction()
            trans.replace(R.id.frame, secondFrag)
            trans.addToBackStack(null)
            trans.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
            trans.commit()
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
}