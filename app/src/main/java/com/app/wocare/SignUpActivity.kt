package com.app.wocare

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView

class SignUpActivity : AppCompatActivity() {

    lateinit var signIn: TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        signIn = findViewById(R.id.tvSignIn)

        signIn.setOnClickListener { startActivity(Intent(this, LoginActivity::class.java)) }
    }
}