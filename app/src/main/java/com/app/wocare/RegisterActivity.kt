package com.app.wocare

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.FragmentTransaction
import com.app.wocare.registerFragments.Step1Fragment

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