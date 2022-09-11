package com.example.expertgoggles

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.speech.RecognitionListener
import android.speech.RecognizerIntent
import android.speech.SpeechRecognizer
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.view.View.OnTouchListener
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.firebase.database.*
import java.util.*


class Audio : AppCompatActivity() {
    private lateinit var speechRecognizer: SpeechRecognizer
    private lateinit var editText: EditText
    private lateinit var micButton: ImageView
    lateinit var firebaseDatabase: FirebaseDatabase
    lateinit var databaseReferenceA: DatabaseReference
    lateinit var tutorialsName : String

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.audio)
        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.RECORD_AUDIO
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            checkPermission()
        }

        firebaseDatabase = FirebaseDatabase.getInstance()
        databaseReferenceA = firebaseDatabase.getReference()

        editText = findViewById(R.id.text)
        micButton = findViewById(R.id.button)
        speechRecognizer = SpeechRecognizer.createSpeechRecognizer(this)
        val speechRecognizerIntent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)
        speechRecognizerIntent.putExtra(
            RecognizerIntent.EXTRA_LANGUAGE_MODEL,
            RecognizerIntent.LANGUAGE_MODEL_FREE_FORM
        )
        speechRecognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault())
        speechRecognizer.setRecognitionListener(object : RecognitionListener {
            override fun onReadyForSpeech(bundle: Bundle) {}
            override fun onBeginningOfSpeech() {
                editText.setText("")
                editText.setHint("Listening...")
            }

            override fun onRmsChanged(v: Float) {}
            override fun onBufferReceived(bytes: ByteArray) {}
            override fun onEndOfSpeech() {
            }
            override fun onError(i: Int) {}
            override fun onResults(bundle: Bundle) {
                micButton.setImageResource(R.drawable.microphone)
                val data = bundle.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION)
                editText.setText(data!![0])



                val convertedString : String = editText.text.toString()
                val userAudio =userAudio()
                userAudio.setText(convertedString)
                userAudio.setUserName(tutorialsName)
                if(tutorialsName=="Dhaiya")
                    userAudio.setUserStatus("b")
                else
                    if(tutorialsName=="Kamakshi") userAudio.setUserStatus("d")
                    else
                        if(tutorialsName=="Ravindra") userAudio.setUserStatus("s")

                // we are use add value event listener method
                // which is called with database reference.
                val postListener = object : ValueEventListener {

                    override fun onDataChange(snapshot: DataSnapshot) {
                        // inside the method of on Data change we are setting
                        // our object class to our database reference.
                        // data base reference will sends data to firebase.
                        databaseReferenceA.push().setValue(userAudio)

                        // after adding this data we are showing toast message.
                        Toast.makeText(this@Audio, "added", Toast.LENGTH_SHORT).show()
                    }

                    override fun onCancelled(error: DatabaseError) {
                        // if the data is not added or it is cancelled then
                        // we are displaying a failure toast message.
                        Toast.makeText(this@Audio, "Fail to add data $error", Toast.LENGTH_SHORT)
                            .show()
                    }
                }
                databaseReferenceA.addListenerForSingleValueEvent((postListener))


            }

            override fun onPartialResults(bundle: Bundle) {}
            override fun onEvent(i: Int, bundle: Bundle) {}
        })


        val spinner = findViewById<Spinner>(R.id.spinner)
        val arrayList: ArrayList<String> = ArrayList()
        arrayList.add("Dhaiya")
        arrayList.add("Ravindra")
        arrayList.add("Kamakshi")
        arrayList.add("Vikram")

        val arrayAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, arrayList)
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = arrayAdapter
        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View,
                position: Int,
                id: Long
            ) {
                tutorialsName = parent.getItemAtPosition(position).toString()
                Toast.makeText(parent.context, "Selected: $tutorialsName", Toast.LENGTH_LONG).show()
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }


        micButton.setOnTouchListener(OnTouchListener { view, motionEvent ->
            if (motionEvent.action == MotionEvent.ACTION_UP) {
                speechRecognizer.stopListening()
            }
            if (motionEvent.action == MotionEvent.ACTION_DOWN) {
                micButton.setImageResource(R.drawable.microphone)
                speechRecognizer.startListening(speechRecognizerIntent)

            }
            false
        })


    }

    override fun onDestroy() {
        super.onDestroy()
        speechRecognizer!!.destroy()
    }

    private fun checkPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.RECORD_AUDIO),
                RecordAudioRequestCode
            )
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == RecordAudioRequestCode && grantResults.size > 0) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) Toast.makeText(
                this,
                "Permission Granted",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    companion object {
        const val RecordAudioRequestCode = 1
    }
}