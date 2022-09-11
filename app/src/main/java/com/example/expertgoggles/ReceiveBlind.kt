package com.example.expertgoggles

import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.util.Log
import android.widget.Toast
import androidx.annotation.NonNull
import androidx.appcompat.app.AppCompatActivity
import com.airbnb.lottie.LottieAnimationView
import com.google.firebase.database.*
import java.util.*


class ReceiveBlind : AppCompatActivity(), TextToSpeech.OnInitListener {

    lateinit var textToSpeech: TextToSpeech
    var firebaseDatabase: FirebaseDatabase? = null
    var databaseReference: DatabaseReference? = null
    lateinit var feed : String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.receivelayout)

        firebaseDatabase = FirebaseDatabase.getInstance();

        // below line is used to get
        // reference for our database.
        databaseReference = firebaseDatabase!!.getReference().ref
       // var newClientKey = database.ref().child('leads').push().key;


        getdata();


        textToSpeech = TextToSpeech(this,this)
        var btn: LottieAnimationView = findViewById(R.id.speak)
        btn.setOnClickListener {


            textToSpeech.speak(feed, TextToSpeech.QUEUE_FLUSH, null)
        }

    }


    private fun getdata() {

        // calling add value event listener method
        // for getting the values from database.
        databaseReference!!.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(@NonNull snapshot: DataSnapshot) {


                for (child in snapshot.getChildren()) {
                    //Log.i("gul", child.key!!)
                    Log.d("gul", "Value is: ${snapshot.getValue()}")

                }
//                val value = snapshot.getValue(String::class.java)
//                if (value != null) {
//                    feed=value
//                }
//                Log.d("tagu",""+value)
            }

            override fun onCancelled(@NonNull error: DatabaseError) {
                // calling on cancelled method when we receive
                // any error or we are not able to get the data.
                Toast.makeText(this@ReceiveBlind, "Fail to get data.", Toast.LENGTH_SHORT).show()
            }
        })
    }


    override fun onInit(status: Int) {
        if (status == TextToSpeech.SUCCESS) {
            var res: Int = 1
            res = textToSpeech.setLanguage(Locale("hin"))

            if (res == TextToSpeech.LANG_MISSING_DATA || res == TextToSpeech.LANG_NOT_SUPPORTED) {
                Toast.makeText(this, "language not supported", Toast.LENGTH_LONG).show()
            }

        }
    }


}