package com.example.wisdom

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_event.*
import com.google.firebase.database.*
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase


class EventActivity : AppCompatActivity() {

    private lateinit var database: DatabaseReference

    private lateinit var eventID: String;
    private lateinit var eventName: String;
    private lateinit var eventDescription: String;
    private lateinit var currentDonation: String;
    private lateinit var goalDonation: String;



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_event)
        database = Firebase.database.reference

        setUI()
    }

    private fun setUI(){
        eventID = intent.getStringExtra("id")


        database.addValueEventListener(object: ValueEventListener{
            override fun onCancelled(p0: DatabaseError) {

            }
            override fun onDataChange(p0: DataSnapshot) {
                eventName = p0.child("event").child(eventID).child("event_name").getValue().toString()
                eventNameView.text = eventName
                eventDescription = p0.child("event").child(eventID).child("description").getValue().toString()
                eventDescriptionView.text = eventDescription
                currentDonation = p0.child("event").child(eventID).child("total_donation").getValue().toString()
                currentDonationView.text = currentDonation
                goalDonation = p0.child("event").child(eventID).child("goal_donation").getValue().toString()
                goalDonationView.text = goalDonation
                progressBarView.progress = ((currentDonation.toFloat()/ goalDonation.toFloat())*100.00).toInt()
            }

        })
    }
}
