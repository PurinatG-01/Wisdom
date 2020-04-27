package com.example.wisdom

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_donate.*
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

class DonateActivity : AppCompatActivity() {

    private lateinit var database: DatabaseReference

    private lateinit var eventID: String
    private lateinit var eventName: String
    private lateinit var selectDonation: String;


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_donate)
        database = Firebase.database.reference

        setUI()
    }


    private fun setUI(){
        eventID = intent.getStringExtra("id")
        database.addValueEventListener(object: ValueEventListener{
            override fun onCancelled(p0: DatabaseError) {}
            override fun onDataChange(p0: DataSnapshot) {
                if(p0.exists()){
                    EventActivity.DownLoadImageTask(smallDisplayImageView).execute(p0.child("event").child(eventID).child("image_url").getValue().toString())

                    eventName = p0.child("event").child(eventID).child("event_name").getValue().toString()
                    eventNameView.text = eventName
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                        val current = LocalDateTime.now()
                        val formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")
                        var answer: String =  current.format(formatter)
                        dateView.text = "Date : "+answer
                    } else {
                        var date = Date();
                        val formatter = SimpleDateFormat("dd/MM/yyyy ")
                        val answer: String = formatter.format(date)
                        dateView.text = "Date : "+answer
                    }

                }
            }
        })
    }

    fun selectAmount(view: View){
        var tag = view.getTag().toString().toInt()
        displaySelectedDonationView.text = tag.toString()
        selectDonation = tag.toString()
    }

    fun submitDonation(view: View){
        var temp1: Int = 0;
        var c = 0;
        database.addValueEventListener(object: ValueEventListener{
            override fun onCancelled(p0: DatabaseError) {}
            override fun onDataChange(p0: DataSnapshot) {
                temp1 = p0.child("event").child(eventID).child("total_donation").getValue().toString().toInt()
                if(c==0){
                    var value = temp1+selectDonation.toInt()
                    database.child("event").child(eventID).child("total_donation").setValue(value.toString())
                }
                c++
            }
        })
        this.finish()
    }



}
