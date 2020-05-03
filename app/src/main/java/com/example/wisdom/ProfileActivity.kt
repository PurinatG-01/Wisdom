package com.example.wisdom

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_profile.*


class ProfileActivity : AppCompatActivity() {

    //  =============== Declare Variable ================
    // DB
    private lateinit var database: DatabaseReference
    // User
    private lateinit var userID : String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        database = Firebase.database.reference
        userID = intent.getStringExtra("userID")

        // =========== Call Set UI =============
        setUI()

    }

    private fun setUI() {

//       Set Realtime DB for Listening to change
        database.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {}
            // Run everytime data change on the Firebase
            override fun onDataChange(p0: DataSnapshot) {
                if (p0.exists()) {
                    // Assign Value
                    nameView.text = p0.child("user").child(userID).child("name").getValue().toString() + "  " + p0.child("user").child(userID).child("surname").getValue().toString()
                    emailView.text = p0.child("user").child(userID).child("email").getValue().toString()
                    userTotalDonationView.text = p0.child("user").child(userID).child("total_donation").getValue().toString()
                }
            }
        })
    }


}