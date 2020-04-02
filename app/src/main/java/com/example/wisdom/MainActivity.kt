package com.example.wisdom

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }


    fun goEvent(view : View){

        val intent = Intent(this, EventActivity::class.java)
        val id = view.id
        print("Hello go to the =>"+id+" Clicked!!")
        startActivity(intent)


    }
}
