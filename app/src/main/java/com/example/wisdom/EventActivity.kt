package com.example.wisdom

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.AsyncTask
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_event.*
import kotlinx.android.synthetic.main.activity_profile.*
import java.net.URL


class EventActivity : AppCompatActivity() {

    //  =============== Declare Variable ================
    // DB
    private lateinit var database: DatabaseReference
    // User
    private lateinit var userID: String
    // Event
    private lateinit var eventID: String;
    private lateinit var eventName: String;
    private lateinit var eventDescription: String;
    private lateinit var currentDonation: String;
    private lateinit var goalDonation: String;


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_event)

        database = Firebase.database.reference

        // ============= Call Set UI ==============
        setUI()


    }

    //  ======================= Setup UI Function ==========================
    private fun setUI(){
        // Assign value to variable
        eventID = intent.getStringExtra("id")
        userID = intent.getStringExtra("userID")

        //       Set Realtime DB for Listening to change
        database.addValueEventListener(object: ValueEventListener{
            override fun onCancelled(p0: DatabaseError) {}
            // Run everytime data change on the Firebase
            override fun onDataChange(p0: DataSnapshot) {

                // Assign value from DB
                eventName = p0.child("event").child(eventID).child("event_name").getValue().toString()
                eventNameView.text = eventName
                eventDescription = p0.child("event").child(eventID).child("description").getValue().toString()
                eventDescriptionView.text = eventDescription
                currentDonation = p0.child("event").child(eventID).child("total_donation").getValue().toString()
                currentDonationView.text = currentDonation
                goalDonation = p0.child("event").child(eventID).child("goal_donation").getValue().toString()
                goalDonationView.text = goalDonation
                progressBarView.progress = ((currentDonation.toFloat()/ goalDonation.toFloat())*100.00).toInt()

                // Lock Donate button if reach the goal donation
                if(goalDonation.toInt() <= currentDonation.toInt()) {
                    donateButtonView.text = getString(R.string.complete)
                }
                // Set Image View
                DownLoadImageTask(eventImageView)
                    .execute(p0.child("event").child(eventID).child("image_url").getValue().toString())

            }

        })

    }

    //  ======================= Intent Navigate Function ==========================
    fun goDonate(view: View){
        val intent = Intent(this, DonateActivity::class.java)
        val id = view.id

        if(goalDonation.toInt() > currentDonation.toInt()){
            intent.putExtra("id",this.eventID)
            intent.putExtra("userID",userID)
            startActivity(intent)
        }

    }

//  **** ============================== Image Setting Class ============================== ****
    public class DownLoadImageTask(internal val imageView: ImageView) : AsyncTask<String, Void, Bitmap?>() {
        override fun doInBackground(vararg urls: String): Bitmap? {
            val urlOfImage = urls[0]
            return try {
                val inputStream = URL(urlOfImage).openStream()
                BitmapFactory.decodeStream(inputStream)
            } catch (e:Exception) { // Catch the download exception
                e.printStackTrace()
                null
            }
        }
        override fun onPostExecute(result: Bitmap?) {
            if(result!=null){
                // Display the downloaded image into image view
                imageView.setImageBitmap(result)
            }else{
                Toast.makeText(imageView.context,"Error downloading image",Toast.LENGTH_SHORT).show()
            }
        }
    }

}
