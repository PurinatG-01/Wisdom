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

    private lateinit var database: DatabaseReference
    private lateinit var userID: String
    private  lateinit var list_row: ArrayList<HistoryRow>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_history)

        database = Firebase.database.reference
        userID = intent.getStringExtra("userID")
        list_row = ArrayList()

        setUI(this)



    }

    private fun setUI(context: Context) {

        database.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {}
            override fun onDataChange(p0: DataSnapshot) {
                if(p0.exists()){
                    var list_event = p0.child("user").child(userID).child("list_event").children
                    var events = p0.child("event")

                    var eventName = ""
                    var date = ""
                    var donation = ""
                        historyTimesView.text = list_event.count().toString()
                    for(donated in p0.child("user").child(userID).child("list_event").children){
                        eventName = p0.child("event").child(donated.child("event_id").getValue().toString()).child("event_name").getValue().toString()
                        date = donated.child("date").getValue().toString()
                        donation = donated.child("donation").getValue().toString()
                        list_row.add(HistoryRow(event_name = eventName, total_donated = donation, date = date))



                    }

                    Collections.reverse(list_row)

                    Toast.makeText(context,"Successfully",Toast.LENGTH_LONG).show()

                    val adapter = HistoryRowAdapter(list_row,context)

                    recyclerView.layoutManager = LinearLayoutManager(context)
                    recyclerView.adapter = adapter








                }
            }
        })
    }
}
