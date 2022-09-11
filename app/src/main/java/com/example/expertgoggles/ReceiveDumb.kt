package com.example.expertgoggles

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.annotation.NonNull
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.*
import java.util.*


class ReceiveDumb : AppCompatActivity() {


    var firebaseDatabase: FirebaseDatabase? = null
    var databaseReference: DatabaseReference? = null
    lateinit var feed : String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.receivelayoutdumb)

        firebaseDatabase = FirebaseDatabase.getInstance();

        // below line is used to get
        // reference for our database.
        databaseReference = firebaseDatabase!!.getReference();

        getdata();

    }


    private fun getdata() {

        // calling add value event listener method
        // for getting the values from database.
        databaseReference!!.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(@NonNull snapshot: DataSnapshot) {

                for (child in snapshot.getChildren()) {
                    child.key?.let { Log.i(TAG, it) }
                    child.getValue(String::class.java)?.let { Log.i(TAG, it) }
                }

//                val value = snapshot.getValue(String::class.java)
//                if (value != null) {
//                    feed=value
//                }
            }

            override fun onCancelled(@NonNull error: DatabaseError) {
                // calling on cancelled method when we receive
                // any error or we are not able to get the data.
                Toast.makeText(this@ReceiveDumb, "Fail to get data.", Toast.LENGTH_SHORT).show()
            }
        })
    }


}