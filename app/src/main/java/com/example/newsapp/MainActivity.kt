package com.example.newsapp

import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase


class MainActivity : AppCompatActivity() {
    val RC_SIGN_IN=123
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        auth = Firebase.auth

        val intent = Intent(this, HomeActivity::class.java)//startActivity(intent)
        val loginBtn:Button=findViewById(R.id.loginBtn)
        val regBtn:TextView=findViewById(R.id.register_now)
        val loginEmail:EditText=findViewById(R.id.loginEmail)
        val loginPassword:EditText=findViewById(R.id.loginPassword)
        val googleBtn:ImageView=findViewById(R.id.googleLogin)


        // Configure sign-in to request the user's ID, email address, and basic
// profile. ID and basic profile are included in DEFAULT_SIGN_IN.
        // Configure sign-in to request the user's ID, email address, and basic
// profile. ID and basic profile are included in DEFAULT_SIGN_IN.
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            .build()
        var googleSigninClient = GoogleSignIn.getClient(this, gso)


        loginBtn.setOnClickListener(object : View.OnClickListener{
            override fun onClick(view:View?){
                val email=loginEmail.text.toString()
                val pass=loginPassword.text.toString()
                if(email != null && pass != null) {
                    loginProcedure(email,pass)
                }
                else
                {
                    Toast.makeText(
                        baseContext,
                        "Authentication failed.",
                        Toast.LENGTH_SHORT,
                    ).show()
                }
            }
        })

        googleBtn.setOnClickListener(object : View.OnClickListener{
            override fun onClick(view:View?){
                //loginProcedure()
                val signInIntent = googleSigninClient.signInIntent
                startActivityForResult(signInIntent, RC_SIGN_IN)
            }
        })

        regBtn.setOnClickListener(/* l = */ object : View.OnClickListener{
            override fun onClick(view: View?) {
                val intent1 = Intent(applicationContext,CreateAccount::class.java)
                startActivity(intent1)
            }

        })
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == RC_SIGN_IN) {
            val intent = Intent(applicationContext,HomeActivity::class.java)
            startActivity(intent)
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
        }
    }
    fun loginProcedure(email:String,password:String){
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(TAG, "signInWithEmail:success")
                    val intent1 = Intent(applicationContext,HomeActivity::class.java)
                    startActivity(intent1)

                } else {
                    // If sign in fails, display a message to the user.
                    Log.w(TAG, "signInWithEmail:failure", task.exception)
                    Toast.makeText(
                        baseContext,
                        "Authentication failed.",
                        Toast.LENGTH_SHORT,
                    ).show()
                }
            }
    }

}