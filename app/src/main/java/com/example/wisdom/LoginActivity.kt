package com.example.wisdom

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        // ...
        // Initialize Firebase Auth
        auth = FirebaseAuth.getInstance()
    }



    fun goSignup(view : View){
        val intent = Intent(this, SignupActivity::class.java)
        startActivity(intent)
    }

    fun goLogin(view : View){

        val email = emailText.text.toString()
        val password = passwordText.text.toString()

        print("Login Method")

//        Hold for authentication on firebase system

        if(email != "" && password != ""){
            auth.signInWithEmailAndPassword(email,password).addOnCompleteListener(this){
                    task ->
                if(task.isSuccessful){
                    errorText.text = ""
                    val intent = Intent(this@LoginActivity,MainActivity::class.java)
                    intent.putExtra("email",email)
                    startActivity(intent)
                }else{
                    errorText.text = "Incorrect email or password"
                }
            }
        }


    }



}
