package com.app.wocare

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.view.LayoutInflater
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import com.google.android.material.bottomsheet.BottomSheetDialog

class LoginActivity : AppCompatActivity() {

    lateinit var edPass: EditText
    lateinit var show: ImageView
    private lateinit var forgotPass: TextView
    private lateinit var signUp: TextView
    private var drawableId: Int = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        edPass = findViewById(R.id.edPassword)
        show = findViewById(R.id.show)
        forgotPass = findViewById(R.id.tvForgotPass)
        signUp = findViewById(R.id.tvSignUp)
        show.tag = R.drawable.eye_slash

        show.setOnClickListener {
            //  Show/Hide Password
            if (show.tag == R.drawable.baseline_eye_24) {
                edPass.transformationMethod = PasswordTransformationMethod.getInstance()
                show.tag = R.drawable.eye_slash
            } else if (show.tag == R.drawable.eye_slash) {
                edPass.transformationMethod = HideReturnsTransformationMethod.getInstance()
                show.tag = R.drawable.baseline_eye_24
            }
            drawableId = show.tag as Int
            show.setImageResource(drawableId)
        }

        signUp.setOnClickListener {
            startActivity(Intent(this@LoginActivity, SignUpActivity::class.java))
        }

        forgotPass.setOnClickListener {
            val nothing = null
            val dialog = BottomSheetDialog(this, R.style.bottomDialogStyle)
            val v = layoutInflater.inflate(R.layout.bottom_sheet, nothing)
            dialog.setContentView(v)
            dialog.setCancelable(true)
            dialog.show()
        }

        edPass.setOnClickListener{

        }
    }
}