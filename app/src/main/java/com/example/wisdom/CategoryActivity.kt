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

    private lateinit var database: DatabaseReference

    private lateinit var categoryName : String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_category)
        database = Firebase.database.reference

        setUI(this)

    }

    private fun setUI(context: Context){
        eventTable.removeAllViews()
        categoryName = intent.getStringExtra("categoryName")
        categoryNameView.text = categoryName
        database.addValueEventListener(object: ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {}
            override fun onDataChange(p0: DataSnapshot) {

                    categoryDesciptionView.text = p0.child("category").child(categoryName).child("c_description").getValue().toString()
                    var i = 0
                    for(event in p0.child("category").child(categoryName).child("c_events").children){
                        var tempEventID = event.child("id").getValue().toString()
                        var row = TableRow(context)

                        var rowLinearView = LinearLayout(context)

                        var eventName = TextView(context)
                        eventName.id = (tempEventID).toInt()
                        eventName.text = p0.child("event").child(tempEventID).child("event_name").getValue().toString()

                        val eventDes = TextView(context)
                        eventDes.text = p0.child("event").child(tempEventID).child("description").getValue().toString()
                        rowLinearView.orientation = LinearLayout.VERTICAL

                        var  param: RelativeLayout.LayoutParams = RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,100);
                        param.setMargins(0,60,0,10)

                        eventName.layoutParams = param
                        eventName.setTextSize(18F)
                        eventName.setTextColor(Color.parseColor("#2EA562"))
                        rowLinearView.addView(eventName)
                        rowLinearView.addView(eventDes)

                        row.addView(rowLinearView)
                        row.setTag(tempEventID)
                        row.setOnClickListener{ v -> goEvent(v)}

                        eventTable.addView(row)
                        i++
                    }

            }


        })

    }
    fun goEvent(view : View){

        val intent = Intent(this, EventActivity::class.java)
        intent.putExtra("id",view.getTag().toString())
        startActivity(intent)


    }


}
