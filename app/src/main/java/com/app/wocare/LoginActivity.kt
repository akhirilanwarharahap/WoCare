package com.app.wocare

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import com.app.wocare.models.UserDetails
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.tasks.Task
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.ktx.Firebase

class LoginActivity : AppCompatActivity() {

    private lateinit var password: EditText
    private lateinit var email: EditText
    private lateinit var show: ImageView
    private lateinit var forgotPass: TextView
    private lateinit var signUp: TextView
    private lateinit var login: TextView
    private lateinit var loginWithGoogle: TextView
    private lateinit var googleSignInClient: GoogleSignInClient
    private var drawableId: Int = 0
    private var firebaseAuth: FirebaseAuth = Firebase.auth
    private lateinit var uid: String

    override fun onStart() {
        super.onStart()
        val current = firebaseAuth.currentUser
        if (current != null){
            val uid = current.uid
            checkIfDataRegisterNotComplete(uid)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        //  define Id
        password = findViewById(R.id.edPassword)
        show = findViewById(R.id.show)
        forgotPass = findViewById(R.id.tvForgotPass)
        signUp = findViewById(R.id.tvSignUp)
        login = findViewById(R.id.login)
        email = findViewById(R.id.edEmail)
        loginWithGoogle = findViewById(R.id.loginGoogle)
        show.tag = R.drawable.eye_slash
        val currentAccount = firebaseAuth.currentUser
        if (currentAccount != null){
            uid = currentAccount.uid
        }

        login.setOnClickListener {
            val email = email.text.toString()
            val password = password.text.toString()

            if (email.isEmpty()){
                messageError("Email Masih kosong...")
            } else if (password.isEmpty()){
                messageError("Password Masih Kosong...")
            } else {
                signInWithEmailAndPass(email, password)
            }
        }

        show.setOnClickListener {
            //  Show/Hide Password
            if (show.tag == R.drawable.baseline_eye_24) {
                password.transformationMethod = PasswordTransformationMethod.getInstance()
                show.tag = R.drawable.eye_slash
            } else if (show.tag == R.drawable.eye_slash) {
                password.transformationMethod = HideReturnsTransformationMethod.getInstance()
                show.tag = R.drawable.baseline_eye_24
            }
            drawableId = show.tag as Int
            show.setImageResource(drawableId)
        }

        signUp.setOnClickListener {
            intentActivity(SignUpActivity::class.java)
        }

        forgotPass.setOnClickListener {
            val nothing = null
            val dialog = BottomSheetDialog(this, R.style.bottomDialogStyle)
            val v = layoutInflater.inflate(R.layout.bottom_sheet, nothing)
            dialog.setContentView(v)
            dialog.setCancelable(true)
            dialog.show()
        }

        loginWithGoogle.setOnClickListener {
            val loginIntent = googleSignInClient.signInIntent
            launcher.launch(loginIntent)
        }
    }

    private fun signInWithEmailAndPass(email: String, pass: String) {
        firebaseAuth.signInWithEmailAndPassword(email, pass)
            .addOnCompleteListener {
                if (it.isSuccessful){
                    checkIfDataRegisterNotComplete(uid)
                } else {
                    messageError("Login Gagal...")
                }
            }
    }

    private val launcher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
            result ->
        if (result.resultCode == Activity.RESULT_OK){
            val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
            handleResult(task)
        }
    }

    private fun messageError(s: String) {
        Toast.makeText(this,s , Toast.LENGTH_SHORT).show()
    }

    private fun handleResult(task: Task<GoogleSignInAccount>) {
        if (task.isSuccessful){
            val account: GoogleSignInAccount? = task.result
            if (account != null){
                updateUi(account)
            }
        } else {
            messageError("Login failed, try again later")
        }
    }

    private fun updateUi(account: GoogleSignInAccount) {
        val credential = GoogleAuthProvider.getCredential(account.idToken, null)
        firebaseAuth.signInWithCredential(credential).addOnCompleteListener {
            if (it.isSuccessful) {
                intentActivity(HomeActivity::class.java)
            } else {
                messageError("Can't login currently. Try after sometimes")
            }
        }
    }

    private fun intentActivity(activityClass: Class<*>) {
        startActivity(Intent(this@LoginActivity, activityClass))
        finish()
    }

    private fun checkIfDataRegisterNotComplete(uid: String) {
        val db = FirebaseDatabase.getInstance().getReference("Users")

        db.child(uid).addListenerForSingleValueEvent(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()){
                    val checkdata = snapshot.getValue(UserDetails::class.java)
                    if (checkdata != null){
                        val username = checkdata.username
                        val tinggi = checkdata.tinggi
                        val berat = checkdata.berat
                        val tglLahir = checkdata.berat

                        if (!username.isNullOrEmpty() || !tglLahir.isNullOrEmpty() || !tinggi.isNullOrEmpty() || !berat.isNullOrEmpty()){
                            intentActivity(HomeActivity::class.java)
                        } else {
                            intentActivity(RegisterActivity::class.java)
                        }
                    }
                }
            }
            override fun onCancelled(error: DatabaseError) {
                println("Error : $error")
            }
        })
    }
}