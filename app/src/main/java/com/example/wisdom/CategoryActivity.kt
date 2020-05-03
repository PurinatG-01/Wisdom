package com.example.wisdom

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TableRow
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_category.*


class CategoryActivity : AppCompatActivity() {

    //  =============== Declare Variable ================

    private lateinit var database: DatabaseReference
    private lateinit var userID: String
    private lateinit var categoryName : String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_category)

        database = Firebase.database.reference


        // =========== Call Set UI =============
        setUI(this)

    }

    //  ======================= Setup UI Function ==========================
    private fun setUI(context: Context){
//        Reset Child
        eventTable.removeAllViews()

//        Assign Value to the variable
        categoryName = intent.getStringExtra("categoryName")
        userID = intent.getStringExtra("userID")
//       Set Category Name View
        categoryNameView.text = categoryName

//       Set Realtime DB for Listening to change
        database.addValueEventListener(object: ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {}
            // Run everytime data change on the Firebase
            override fun onDataChange(p0: DataSnapshot) {
                //        Reset Child
                eventTable.removeAllViews()
//                Set Category Desciption View
                    categoryDesciptionView.text = p0.child("category").child(categoryName).child("c_description").getValue().toString()

                    // Iterate all value in the category

                    for(event in p0.child("category").child(categoryName).child("c_events").children){
                        // Prepare the child view for Table Layout

                        var row = TableRow(context)
                        var rowLinearView = LinearLayout(context)
                        var eventName = TextView(context)
                        val eventDes = TextView(context)

                        // Assign value from DB
                        var tempEventID = event.child("id").getValue().toString()
                        var eventDescription = p0.child("event").child(tempEventID).child("description").getValue().toString()
                        eventName.id = (tempEventID).toInt()
                        eventName.text = p0.child("event").child(tempEventID).child("event_name").getValue().toString()
                        rowLinearView.orientation = LinearLayout.VERTICAL
                        var  param: RelativeLayout.LayoutParams = RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,100);
                        param.setMargins(0,60,0,10)
                        eventName.layoutParams = param
                        eventName.setTextSize(18F)
                        eventName.setTextColor(Color.parseColor("#2EA562"))
                        row.setTag(tempEventID)
                        row.setOnClickListener{ v -> goEvent(v)}
                        // Set the length of event description
                        if(eventDescription.length >= 25){
                            eventDes.text = eventDescription.substring(0,25) + "..."
                        }else {
                            eventDes.text = eventDescription
                        }

                        // Add child view to parent view
                        rowLinearView.addView(eventName)
                        rowLinearView.addView(eventDes)
                        row.addView(rowLinearView)
                        eventTable.addView(row)
                    }

            }


        })

    }

    //   =============== Intent Navigate Function  ===================
    fun goEvent(view : View){

        val intent = Intent(this, EventActivity::class.java)
        intent.putExtra("id",view.getTag().toString())
        intent.putExtra("userID",userID)
        startActivity(intent)


    }


}
