package com.example.wisdom

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_signup.*

class SignupActivity : AppCompatActivity() {


    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)

        auth = FirebaseAuth.getInstance()

        signupButton.setOnClickListener(){
            signUpUser()
        }

    }

    fun signUpUser(){
        if(emailText.text.toString().isEmpty()){
            emailText.error = "Please enter email"
            emailText.requestFocus()
            return
        }

        if(!Patterns.EMAIL_ADDRESS.matcher(emailText.text.toString()).matches()){
            emailText.error = "Please enter valid email"
            emailText.requestFocus()
            return
        }

        if(passwordText.text.toString().isEmpty()){
            passwordText.error = "Please enter password"
            passwordText.requestFocus()
            return
        }

        if(confirmText.text.toString().isEmpty()){
            confirmText.error = "Please enter password"
            confirmText.requestFocus()
            return
        }

        if(confirmText.text.toString() != passwordText.text.toString()){
            confirmText.error = "Password is not match "
            confirmText.requestFocus()
            return
        }

        auth.createUserWithEmailAndPassword(emailText.text.toString(),passwordText.text.toString()).addOnCompleteListener(this){task ->
            if(task.isSuccessful){
                startActivity(Intent(this,MainActivity::class.java))
                finish()

            }
            else{
                Toast.makeText(baseContext, "Sign up  failed.", Toast.LENGTH_SHORT).show()

            }

        }
    }
}
