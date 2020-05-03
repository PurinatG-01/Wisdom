package com.example.wisdom

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_history.*
import java.util.*
import kotlin.collections.ArrayList

class HistoryActivity : AppCompatActivity() {
    //  =============== Declare Variable ================
    // DB
    private lateinit var database: DatabaseReference
    // User
    private lateinit var userID: String
    // Data in Card View
    private  lateinit var list_row: ArrayList<HistoryRow>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_history)

        database = Firebase.database.reference
        userID = intent.getStringExtra("userID")
        list_row = ArrayList()

        // =========== Call Set UI =============
        setUI(this)



    }

    //  ======================= Set Up UI Function ==========================
    private fun setUI(context: Context) {
//       Set Realtime DB for Listening to change
        database.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {}
            // Run everytime data change on the Firebase
            override fun onDataChange(p0: DataSnapshot) {
                if(p0.exists()){
                    // Prepare variable
                    var list_event = p0.child("user").child(userID).child("list_event").children
                    var events = p0.child("event")
                    var eventName = ""
                    var date = ""
                    var donation = ""
                    // Assign value to donated times
                    historyTimesView.text = list_event.count().toString()
                    // Retreive data from DB
                    for(donated in p0.child("user").child(userID).child("list_event").children){
                        eventName = p0.child("event").child(donated.child("event_id").getValue().toString()).child("event_name").getValue().toString()
                        date = donated.child("date").getValue().toString()
                        donation = donated.child("donation").getValue().toString()
                        list_row.add(HistoryRow(event_name = eventName, total_donated = donation, date = date))
                    }
                    // Reverse data from Latest to Oldest
                    Collections.reverse(list_row)

                    // Connect Data, Adapter, Manager to the class HistoryRowAdapter
                    val adapter = HistoryRowAdapter(list_row,context)
                    recyclerView.layoutManager = LinearLayoutManager(context)
                    recyclerView.adapter = adapter

                    // Show successfully message
                    Toast.makeText(context,"Successfully",Toast.LENGTH_LONG).show()








                }
            }
        })
    }
}
