package com.example.wisdom

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.google.firebase.database.*
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase


import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    @IgnoreExtraProperties
    data class Event(
        var event_name: String? = "",
        var total_donation: String? = ""
    )


    private lateinit var database: DatabaseReference

    private lateinit var userEmail: String

    private lateinit var listCard: MutableList<Event>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        database = Firebase.database.reference
        // Write a message to the database
        userEmail = intent.getStringExtra("email")
        testView.text = userEmail
        listCard = mutableListOf()
        setUI()



    }




    private fun setUI(){
//        database.child("event").child("1").child("event_name")


        database.addValueEventListener(object: ValueEventListener{
            override fun onCancelled(p0: DatabaseError) {

            }

            override fun onDataChange(p0: DataSnapshot) {

                if(p0.exists()){

//                    testView.text = p0.child("event").child("0").child("event_name").getValue().toString()
//                    for(event in p0.children){
//                        val temp = event.getValue(Event::class.java)
//                        listCard.add(temp!!)
//                    }

                    card1Name.text = p0.child("event").child("0").child("event_name").getValue().toString()
                    card1Donation.text = p0.child("event").child("0").child("total_donation").getValue().toString()

                    card2Name.text = p0.child("event").child("1").child("event_name").getValue().toString()
                    card2Donation.text = p0.child("event").child("1").child("total_donation").getValue().toString()

                    card3Name.text = p0.child("event").child("2").child("event_name").getValue().toString()
                    card3Donation.text = p0.child("event").child("2").child("total_donation").getValue().toString()




                }else{

                    testView.text = "No child on event"
                }


            }

        })

    }


    fun goEvent(view : View){

        val intent = Intent(this, EventActivity::class.java)
        val id = view.id

        var eventId = "0";
        when(view.id) {
            R.id.eventCard1 -> {eventId = "0"}
            R.id.eventCard2 -> {eventId = "1"}
            R.id.eventCard3 -> {eventId = "2"}
        }
        print("Hello go to the =>"+id+" Clicked!!")
        intent.putExtra("id",eventId)
        startActivity(intent)


    }

    fun goCategory(view : View){

        val intent = Intent(this, CategoryActivity::class.java)
        val id = view.id

        var categoryName = "";
        when(view.id) {
            R.id.buttonC1 -> {categoryName = "Technology"}
            R.id.buttonC2 -> {categoryName = "Environment"}
            R.id.buttonC3 -> {categoryName = "Festival"}
            R.id.buttonC4 -> {categoryName = "Sport"}
        }
        print("Hello go to the =>"+id+" Clicked!!")
        intent.putExtra("categoryName",categoryName)
        startActivity(intent)


    }

}
