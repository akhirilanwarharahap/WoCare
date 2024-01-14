package com.app.wocare

import android.app.Activity
import android.app.Instrumentation.ActivityResult
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.view.LayoutInflater
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.tasks.Task
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider

class LoginActivity : AppCompatActivity() {

    lateinit var edPass: EditText
    lateinit var edEmail: EditText
    lateinit var show: ImageView
    private lateinit var forgotPass: TextView
    private lateinit var signUp: TextView
    private lateinit var signInBtn : TextView
    private lateinit var loginGoogle : TextView
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var googleSignInClient: GoogleSignInClient
    private var drawableId: Int = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        firebaseAuth = FirebaseAuth.getInstance()
        edEmail = findViewById(R.id.edEmail)
        edPass = findViewById(R.id.edPassword)
        signInBtn = findViewById(R.id.signInBtn)
        loginGoogle = findViewById(R.id.loginGoogle)
        show = findViewById(R.id.show)
        forgotPass = findViewById(R.id.tvForgotPass)
        signUp = findViewById(R.id.tvSignUp)
        show.tag = R.drawable.eye_slash

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.defualt_web_client_id))
            .requestEmail()
            .build()
        googleSignInClient = GoogleSignIn.getClient(this,gso)

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

        signInBtn.setOnClickListener {
            val email = edEmail.text.toString()
            val password = edPass.text.toString()
            if (email.isNotEmpty() && password.isNotEmpty()) {
                firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener {
                    if (it.isSuccessful){
                        val intent = Intent(this, MainActivity::class.java)
                        startActivity(intent)
                    } else {
                        Toast.makeText(this, it.exception.toString(), Toast.LENGTH_SHORT).show()
                    }
                }
            } else {
                Toast.makeText(this, "Fields cannot be empty", Toast.LENGTH_SHORT).show()
            }
        }

        signUp.setOnClickListener {
            startActivity(Intent(this, SignUpActivity::class.java))
        }

        forgotPass.setOnClickListener {
            val nothing = null
            val dialog = BottomSheetDialog(this, R.style.bottomDialogStyle)
            val v = layoutInflater.inflate(R.layout.bottom_sheet, nothing)
            dialog.setContentView(v)
            dialog.setCancelable(true)
            dialog.show()
        }
        loginGoogle.setOnClickListener {
            loginWithGoogle()
        }

    }

    private fun loginWithGoogle(){
        val loginIntent = googleSignInClient.signInIntent
        launcher.launch(loginIntent)
    }

    private val launcher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
        result ->
        if (result.resultCode == Activity.RESULT_OK){
            val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
            handleResult(task)
        }
    }

    private fun handleResult(task: Task<GoogleSignInAccount>){
        if (task.isSuccessful){
            val account:GoogleSignInAccount? = task.result
            if (account!=null){
                updateUI(account)
            }
        } else {
            Toast.makeText(this, "Login failed, try again later", Toast.LENGTH_SHORT).show()
        }
    }

    private fun updateUI(account: GoogleSignInAccount){
        val credential = GoogleAuthProvider.getCredential(account.idToken, null)
        firebaseAuth.signInWithCredential(credential).addOnCompleteListener {
            if (it.isSuccessful) {
                startActivity(Intent(this, MainActivity::class.java))
                finish()
            } else {
                Toast.makeText(this, "Can't login currently. Try after sometime", Toast.LENGTH_SHORT).show()
            }
        }
    }
}