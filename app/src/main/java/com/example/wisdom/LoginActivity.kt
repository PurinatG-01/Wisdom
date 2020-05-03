package com.example.wisdom

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_login.*
import java.util.*

class LoginActivity : AppCompatActivity() {

//   ======================  Declare variable  ======================
    private lateinit var auth: FirebaseAuth
    lateinit var mBtn : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        // ============== Load language ===============
        loadLocate()

        // ============== Initialize Firebase Auth ===============
        auth = FirebaseAuth.getInstance()



    }


    //   ======================  Change Language Function  =========================
    fun showChangeLang(view: View) {

        val listItmes = arrayOf("Thai","English")
        val mBuilder = AlertDialog.Builder(this@LoginActivity)
        mBuilder.setTitle("Choose Language")
        mBuilder.setSingleChoiceItems(listItmes, -1) { dialog, which ->
            if (which == 0) {
                setLocate("th")
                recreate()
            } else if (which == 1) {
                setLocate("en")
                recreate()
            }

            dialog.dismiss()
        }
        val mDialog = mBuilder.create()

        mDialog.show()

    }



    //   ======================  Set Language Function  ======================
    private fun setLocate(Lang: String?) {

        val locale = Locale(Lang)

        Locale.setDefault(locale)

        val config = Configuration()

        config.locale = locale
        baseContext.resources.updateConfiguration(config, baseContext.resources.displayMetrics)

        val editor = getSharedPreferences("Settings", Context.MODE_PRIVATE).edit()
        editor.putString("My_Lang", Lang)
        editor.apply()
    }

    //   ======================  Load Language Function  ======================
    private fun loadLocate() {
        val sharedPreferences = getSharedPreferences("Settings", Activity.MODE_PRIVATE)
        val language = sharedPreferences.getString("My_Lang", "")
        setLocate(language)
    }




//   ======================  Intent Navigate Function  ======================


    //   ======================  Go Signup Activity  ======================
    fun goSignup(view : View){
        val intent = Intent(this, SignupActivity::class.java)
        startActivity(intent)
    }

    //   ======================  Go Login Activity  ======================
    fun goLogin(view : View){
        val email = emailText.text.toString()
        val password = passwordText.text.toString()
        errorText.text = ""
        print("Login Method")

        // ============== Hold for authentication on firebase system ==============

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
