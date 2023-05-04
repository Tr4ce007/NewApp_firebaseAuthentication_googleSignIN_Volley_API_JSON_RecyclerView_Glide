package com.example.newsapp

import android.content.ContentValues.TAG
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.View.OnClickListener
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class CreateAccount : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_account)

        val regBtn: Button =findViewById(R.id.registerBtn)
        val regName: EditText =findViewById(R.id.signupName)
        val regEmail: EditText =findViewById(R.id.signupEmail)
        val regPassword: EditText =findViewById(R.id.signupPassword)
        val regMobile:EditText=findViewById(R.id.signupMobile)

        auth = Firebase.auth

        regBtn.setOnClickListener(object :View.OnClickListener{
            override fun onClick(view:View?){
                var email= regEmail.text.toString()
                var pass = regPassword.text.toString()
                if(email!=null && pass !=null) {
                    registrationProcedure(email, pass)
                }
                else{
                    Toast.makeText(
                        baseContext,
                        "Empty Fields Not Allowed",
                        Toast.LENGTH_SHORT,
                    ).show()
                }
            }
        })


    }
    fun registrationProcedure(email:String,password:String) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(TAG, "createUserWithEmail:success")
                    val intent1 = Intent(applicationContext,HomeActivity::class.java)
                    startActivity(intent1)
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w(TAG, "createUserWithEmail:failure", task.exception)
                    Toast.makeText(
                        baseContext,
                        "Authentication failed.",
                        Toast.LENGTH_SHORT,
                    ).show()
                }
            }
    }

}