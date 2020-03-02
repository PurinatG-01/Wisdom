package com.example.wisdom

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
    }

    fun goSignup(view : View){
        val intent = Intent(this, SignupActivity::class.java)
        startActivity(intent)
    }

    fun goLogin(view : View){

        val email = emailText.text.toString()
        val password = passwordText.text.toString()

//        Hold for authentication on firebase system
        startActivity(Intent(this,MainActivity::class.java))

    }
}
