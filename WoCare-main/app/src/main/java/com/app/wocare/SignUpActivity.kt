package com.app.wocare

import android.util.Log
import android.app.Activity
import android.content.Intent
import android.nfc.Tag
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import java.util.logging.Logger
import kotlin.math.log


class SignUpActivity : AppCompatActivity() {

    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var signupBtn: TextView
    private lateinit var tvLogin: TextView
    private lateinit var signGoogle: TextView
    private lateinit var signFacbook: TextView
    private lateinit var email: EditText
    private lateinit var username: EditText
    private lateinit var passowrd: EditText
    private lateinit var confirmPassword: EditText
    private lateinit var googleSignInClient: GoogleSignInClient
    lateinit var callbackManager: CallbackManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)
        firebaseAuth = FirebaseAuth.getInstance()
        email = findViewById(R.id.edEmail)
        tvLogin = findViewById(R.id.tvSignIn)
        username = findViewById(R.id.edUsername)
        passowrd = findViewById(R.id.edPassword)
        confirmPassword = findViewById(R.id.edConfirmPass)
        signupBtn = findViewById(R.id.signBtn)
        signGoogle = findViewById(R.id.signUpGoogle)
        signFacbook = findViewById(R.id.signUpFacebook)

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.defualt_web_client_id))
            .requestEmail()
            .build()
        googleSignInClient = GoogleSignIn.getClient(this,gso)

        signupBtn.setOnClickListener {
            val email = email.text.toString()
            val username = username.text.toString()
            val password = passowrd.text.toString()
            val confirmPass = confirmPassword.text.toString()

            if (email.isNotEmpty() && username.isNotEmpty() && password.isNotEmpty() && confirmPass.isNotEmpty()) {
                if (password == confirmPass) {
                    firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener {
                        if (it.isSuccessful){
                            val intent = Intent(this,  MainActivity::class.java)
                            startActivity(intent)
                        } else {
                            Toast.makeText(this, it.exception.toString(), Toast.LENGTH_SHORT).show()
                        }
                    }
                } else {
                    Toast.makeText(this, "Password does not matched", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, "Fields cannot be empty", Toast.LENGTH_SHORT).show()
            }
        }
        tvLogin.setOnClickListener {
            val loginIntent = Intent(this, LoginActivity::class.java)
            startActivity(loginIntent)
        }
        signGoogle.setOnClickListener {
            signInWithGoogle()
        }

        callbackManager = CallbackManager.Factory.create()

        signFacbook.setOnClickListener {
            LoginManager.getInstance().logInWithReadPermissions(this, listOf("email","public_profile"))
            LoginManager.getInstance().registerCallback(callbackManager, object : FacebookCallback<LoginResult> {
                override fun onSuccess(result: LoginResult) {
                    Log.d("success", "Facebook token: " + result.accessToken.token)
                }
                override fun onCancel() {
                    Log.d("cancel", "Facebook onCancel")
                }

                override fun onError(error: FacebookException) {
                    Log.d("error", "Facebook onError")
                }
            })
        }
    }

    //method signIn with Google
    private fun signInWithGoogle(){
        val sigInIntent = googleSignInClient.signInIntent
        launcher.launch(sigInIntent)
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
            Toast.makeText(this, "SigIn failed, try again later", Toast.LENGTH_SHORT).show()
        }
    }

    private fun updateUI(account: GoogleSignInAccount) {
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