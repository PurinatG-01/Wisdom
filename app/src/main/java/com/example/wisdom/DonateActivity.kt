package com.example.wisdom

import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.*
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
    private lateinit var selectDonation: String
    private lateinit var dateD: String

    @IgnoreExtraProperties
    data class donation(
        var event_id: String? = "",
        var donation: Int? = 0,
        var date: String? = "XX/XX/XX"

    )


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_donate)
        database = Firebase.database.reference
        dateD = getDate()
        setUI()
    }


    private fun setUI() {
        eventID = intent.getStringExtra("id")
        database.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {}
            override fun onDataChange(p0: DataSnapshot) {
                if (p0.exists()) {
                    EventActivity.DownLoadImageTask(smallDisplayImageView).execute(
                        p0.child("event").child(eventID).child("image_url").getValue().toString()
                    )

                    eventName =
                        p0.child("event").child(eventID).child("event_name").getValue().toString()
                    eventNameView.text = eventName

                    dateView.text = dateD

                }
            }
        })

    }

    fun selectAmount(view: View) {
        var tag = view.getTag().toString().toInt()
        displaySelectedDonationView.text = tag.toString()
        selectDonation = tag.toString()
    }

    fun submitDonation(view: View) {
        var userID = intent.getStringExtra("userID")
        val builder = AlertDialog.Builder(this)
        // Set the alert dialog title
        builder.setTitle(getString(R.string.confirmdonation))

        // Display a message on alert dialog
        builder.setMessage(getString(R.string.doyouwant))

        // Set a positive button and its click listener on alert dialog
        builder.setPositiveButton((R.string.yes)) { dialog, which ->
            var temp1: Int = 0;
            var temp2: Int = 0;
            var c = 0;
            database.addValueEventListener(object : ValueEventListener {
                override fun onCancelled(p0: DatabaseError) {}
                override fun onDataChange(p0: DataSnapshot) {
                    temp1 = p0.child("event").child(eventID).child("total_donation").getValue()
                        .toString().toInt()
                    temp2 =
                        p0.child("user").child(userID).child("total_donation").getValue().toString()
                            .toInt()
                    if (c == 0) {
                        var value = temp1 + selectDonation.toInt()
                        var value2 = temp2 + selectDonation.toInt()
                        database.child("user").child(userID).child("list_event").push().setValue(
                            donation(
                                event_id = eventID,
                                donation = selectDonation.toInt(),
                                date = dateD
                            )
                        )
                        database.child("user").child(userID).child("total_donation")
                            .setValue(value2)
                        database.child("event").child(eventID).child("total_donation")
                            .setValue(value.toString())
                    }
                    c++
                }
            })


            // Do something when user press the positive button
            Toast.makeText(applicationContext, R.string.donationcomplete, Toast.LENGTH_SHORT).show()
            this.finish()
        }

        // Display a neutral button on alert dialog
        builder.setNeutralButton(R.string.donationcancel) { _, _ ->
        }

        // Make the alert dialog using builder
        val dialog: AlertDialog = builder.create()

        // Display the alert dialog on app interface
        dialog.show()
    }


    private fun getDate(): String {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val current = LocalDateTime.now()
            val formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")
            return current.format(formatter)

        } else {
            var date = Date();
            val formatter = SimpleDateFormat("dd/MM/yyyy ")
            return formatter.format(date)
        }
    }
}

