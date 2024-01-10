package com.app.wocare

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView

class MainActivity : AppCompatActivity() {

    private lateinit var btnSkip: TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btnSkip = findViewById(R.id.tvSkip)

        btnSkip.setOnClickListener{
            startActivity(Intent(this@MainActivity, LoginActivity::class.java))
        }

        if (savedInstanceState == null){
            val firstFrag = FragmentInfo1()
            val fragTrans = supportFragmentManager.beginTransaction()
            fragTrans.add(R.id.placeholder, firstFrag)
            fragTrans.disallowAddToBackStack()
            fragTrans.commit()
        }
    }
}