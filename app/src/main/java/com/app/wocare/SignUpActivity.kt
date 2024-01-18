package com.app.wocare

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.app.wocare.models.UserDetails
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class SignUpActivity : AppCompatActivity() {

    private lateinit var signIn: TextView
    private lateinit var signUp: TextView
    private lateinit var btnShowPass1: ImageView
    private lateinit var btnShowPass2: ImageView
    private lateinit var email: EditText
    private lateinit var pass: EditText
    private lateinit var passConfirm: EditText
    private lateinit var mAuth: FirebaseAuth
    private var drawableId: Int = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        //  define Id
        signIn = findViewById(R.id.tvSignIn)
        signUp = findViewById(R.id.tvSignUp)
        btnShowPass1 = findViewById(R.id.show)
        btnShowPass2 = findViewById(R.id.show1)
        email = findViewById(R.id.edEmail)
        pass = findViewById(R.id.edPassword)
        passConfirm = findViewById(R.id.edConfirmPass)
        mAuth = FirebaseAuth.getInstance()

        setTag(R.drawable.eye_slash)

        btnShowPass1.setOnClickListener {
            showHidePass(btnShowPass1, pass)
        }

        btnShowPass2.setOnClickListener {
            showHidePass(btnShowPass2, passConfirm)
        }

        signUp.setOnClickListener {
            val dataEmail = email.text.toString()
            val dataPass = pass.text.toString()
            val dataConfirmPass = passConfirm.text.toString()

            if (dataEmail.isEmpty()){
                messageError("Email Masih Kosong...")
            } else if (dataPass.isEmpty()){
                messageError("Password Masih Kosong...")
            } else if (dataConfirmPass.isEmpty()){
                messageError("Password Confirm Masih Kosong...")
            } else if (dataPass === dataConfirmPass){
                messageError("Password tidak sama")
            } else {
                mAuth.createUserWithEmailAndPassword(dataEmail, dataPass)
                    .addOnCompleteListener {
                        if (it.isSuccessful){

                            val fUser = mAuth.currentUser
                            val uid = fUser!!.uid
                            val newUser = UserDetails("", "", dataEmail, "", "", "", "")
                            val db = FirebaseDatabase.getInstance().getReference("Users")

                            db.child(uid).setValue(newUser).addOnCompleteListener {it2 ->
                                if (it2.isSuccessful){
                                    val i = Intent(this, RegisterActivity::class.java)
                                    i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
                                    startActivity(i)
                                    finish()
                                } else {
                                    messageError("Account register Failed")
                                }
                            }
                        } else {
                            messageError("Account register Failed")
                        }
                    }
            }
        }
        signIn.setOnClickListener { startActivity(Intent(this, LoginActivity::class.java)) }
    }

    private fun messageError(s: String) {
        Toast.makeText(this, s , Toast.LENGTH_SHORT).show()
    }

    private fun setTag(eyeSlash: Int) {
        btnShowPass1.tag = eyeSlash
        btnShowPass2.tag = eyeSlash
    }

    private fun showHidePass(showPass: ImageView, edPass: EditText) {
        //  Show/Hide Password
        if (showPass.tag == R.drawable.baseline_eye_24) {
            edPass.transformationMethod = PasswordTransformationMethod.getInstance()
            showPass.tag = R.drawable.eye_slash
        } else if (showPass.tag == R.drawable.eye_slash) {
            edPass.transformationMethod = HideReturnsTransformationMethod.getInstance()
            showPass.tag = R.drawable.baseline_eye_24
        }
        drawableId = showPass.tag as Int
        showPass.setImageResource(drawableId)
    }
}