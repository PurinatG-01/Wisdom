package com.example.wisdom

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import android.widget.Toolbar
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.os.ConfigurationCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.Navigation.findNavController
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.navigation.NavigationView
import com.google.firebase.database.*
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_event.*

import kotlinx.android.synthetic.main.activity_main.*
import java.net.URL
import java.util.*

class MainActivity : AppCompatActivity() {

    private lateinit var database: DatabaseReference

    private lateinit var userEmail: String
    private lateinit var userID: String

    lateinit var drawerLayout: DrawerLayout
    lateinit var navView: NavigationView
    lateinit var toolbar: androidx.appcompat.widget.Toolbar


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        database = Firebase.database.reference
        // Write a message to the database
        userEmail = intent.getStringExtra("email")



        setUI()
        drawerLayout = findViewById(R.id.drawer_layout)
        navView = findViewById(R.id.nav)
        toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        val toggle = ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)

            drawerLayout.addDrawerListener(toggle)
            toggle.syncState()
            toggle.isDrawerIndicatorEnabled = true
            supportActionBar?.setHomeButtonEnabled(true)
            supportActionBar?.setDisplayShowTitleEnabled(false);



    }


    fun onClickLogout(item: MenuItem){
        val nextView = Intent(this, LoginActivity::class.java)
        startActivity(nextView)

    }

    fun onClickHome(item: MenuItem) {
        val nextView = Intent(this, MainActivity::class.java)
        startActivity(nextView)
    }


    private fun setUI() {

        database.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {}
            override fun onDataChange(p0: DataSnapshot) {
                if (p0.exists()) {

//                  ==============================  User Setup ==============================
                    for(user in p0.child("user").children){
                        if(user.child("email").getValue().toString().equals(userEmail)){
                            userID = user.key.toString()
                            userNameView.text = user.child("name").getValue().toString()
                            break;
                        }
                    }

//                  ==============================  Event Setup ==============================

                    card1Name.text =
                        p0.child("event").child("0").child("event_name").getValue().toString()
                    card1Donation.text =
                        p0.child("event").child("0").child("total_donation").getValue().toString()
                    EventActivity.DownLoadImageTask(eventCard1)
                        .execute(p0.child("event").child("0").child("image_url").getValue().toString())

                    card2Name.text =
                        p0.child("event").child("1").child("event_name").getValue().toString()
                    card2Donation.text =
                        p0.child("event").child("1").child("total_donation").getValue().toString()
                    EventActivity.DownLoadImageTask(eventCard2)
                        .execute(p0.child("event").child("1").child("image_url").getValue().toString())

                    card3Name.text =
                        p0.child("event").child("2").child("event_name").getValue().toString()
                    card3Donation.text =
                        p0.child("event").child("2").child("total_donation").getValue().toString()
                    EventActivity.DownLoadImageTask(eventCard3)
                        .execute(p0.child("event").child("2").child("image_url").getValue().toString())


                }
            }

        })

    }


    fun goEvent(view: View) {

        val intent = Intent(this, EventActivity::class.java)
        val id = view.id

        var eventId = "0";
        when (view.id) {
            R.id.eventCard1 -> {
                eventId = "0"
            }
            R.id.eventCard2 -> {
                eventId = "1"
            }
            R.id.eventCard3 -> {
                eventId = "2"
            }
        }
        intent.putExtra("id", eventId)
        intent.putExtra("userID",userID)
        startActivity(intent)


    }

    fun goCategory(view: View) {

        val intent = Intent(this, CategoryActivity::class.java)
        val id = view.id

        var categoryName = "";
        when (view.id) {
            R.id.buttonC1 -> {
                categoryName = "Technology"
            }
            R.id.buttonC2 -> {
                categoryName = "Environment"
            }
            R.id.buttonC3 -> {
                categoryName = "Festival"
            }
            R.id.buttonC4 -> {
                categoryName = "Sport"
            }
        }
        intent.putExtra("categoryName", categoryName)
        intent.putExtra("userID",userID)
        startActivity(intent)


    }




}
