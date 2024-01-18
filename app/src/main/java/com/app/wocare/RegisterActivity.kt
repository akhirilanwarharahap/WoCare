package com.app.wocare

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.FragmentTransaction

class RegisterActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        if (savedInstanceState == null) {
            val firstFrag = Step1Fragment()
            val fragTrans = supportFragmentManager.beginTransaction()
            fragTrans.add(R.id.frame, firstFrag, "_1")
            fragTrans.disallowAddToBackStack()
            fragTrans.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
            fragTrans.commit()
        }
    }
}