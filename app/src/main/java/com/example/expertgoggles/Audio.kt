package com.example.expertgoggles

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import android.media.MediaPlayer
import android.media.MediaRecorder
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.speech.RecognizerIntent
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import android.widget.Toast
import androidx.annotation.Nullable
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.google.firebase.database.*
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import java.io.IOException
import java.util.*


class Audio : AppCompatActivity() {

    private var mediaRecorder: MediaRecorder? = null
    private var mediaPlayer: MediaPlayer? = null
    private var AudioSavaPath: String? = null
    lateinit var st : StorageReference
    lateinit var firebaseDatabase: FirebaseDatabase
    lateinit var databaseReference: DatabaseReference
    private val REQUEST_CODE_SPEECH_INPUT = 1
    lateinit var convertedText : String
    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.audio)

         var StartRecording: ImageButton? = null
         var StopRecording: Button? = null
         var StartPlaying: Button? = null
         var StopPlaying: Button? = null

        StartRecording = findViewById(R.id.startRecord)
        StopRecording = findViewById(R.id.stopRecord)
        StartPlaying = findViewById(R.id.startPlaying)
        StopPlaying = findViewById(R.id.stopPlaying)

        st = FirebaseStorage.getInstance().getReference()

        databaseReference = FirebaseDatabase.getInstance().getReference("audio")

//        StartRecording.setOnTouchListener(OnTouchListener { v, event ->
//            if (event.action == MotionEvent.ACTION_DOWN) {
//            recordAudio()
//            }
//
//            if (event.action == MotionEvent.ACTION_UP) {
//                recordAudio()
//            }
//
//            return@OnTouchListener false
//        })

        StartRecording.setOnClickListener(View.OnClickListener {
            recordAudio()

            val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)
            intent.putExtra(
                RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM
            )
            intent.putExtra(
                RecognizerIntent.EXTRA_LANGUAGE,
                Locale.getDefault()
            )
            intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Speak to text")

            try {
                startActivityForResult(intent, REQUEST_CODE_SPEECH_INPUT)
            } catch (e: Exception) {
                Toast
                    .makeText(
                        this@Audio, " " + e.message,
                        Toast.LENGTH_SHORT
                    )
                    .show()
            }

        })
        StopRecording.setOnClickListener(View.OnClickListener {
            stopRecord()

            storeAudio()

        })
        StartPlaying.setOnClickListener(View.OnClickListener {
            mediaPlayer = MediaPlayer()
            try {
                mediaPlayer!!.setDataSource(AudioSavaPath)
                mediaPlayer!!.prepare()
                mediaPlayer!!.start()




                Toast.makeText(this@Audio, "Start playing", Toast.LENGTH_SHORT).show()
            } catch (e: IOException) {
                e.printStackTrace()
            }
        })
        StopPlaying.setOnClickListener(View.OnClickListener {

            mediaPlayer?.stop();
            mediaPlayer?.release();
            Toast.makeText(this, "Stopped playing", Toast.LENGTH_SHORT).show();
        })
    }

    override fun onActivityResult(
        requestCode: Int, resultCode: Int,
        @Nullable data: Intent?
    ) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CODE_SPEECH_INPUT) {
            if (resultCode == RESULT_OK && data != null) {
                val result = data.getStringArrayListExtra(
                    RecognizerIntent.EXTRA_RESULTS
                )
//                tv_Speech_to_text.setText(
//                    Objects.requireNonNull(result)[0]
                            convertedText= Objects.requireNonNull(result)!![0]

            }
        }
    }

    private fun checkPermissions(): Boolean {
        val first = ActivityCompat.checkSelfPermission(
            applicationContext,
            Manifest.permission.RECORD_AUDIO
        )
        val second = ActivityCompat.checkSelfPermission(
            applicationContext,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
        )
        return first == PackageManager.PERMISSION_GRANTED &&
                second == PackageManager.PERMISSION_GRANTED
    }

    fun storeAudio()
    {

        val user =userAudio()
        user.setText(convertedText)

//        val uri : Uri = Uri.parse(AudioSavaPath)
//        val filepath : StorageReference = st.child("audio").child(uri.lastPathSegment!!)
//        filepath.putFile(uri).addOnSuccessListener { instanceIdResult ->
//            //good
//            databaseReference.push().setValue(filepath)
//        }


        // we are use add value event listener method
        // which is called with database reference.
        val postListener = object : ValueEventListener {

            override fun onDataChange(snapshot: DataSnapshot) {
                // inside the method of on Data change we are setting
                // our object class to our database reference.
                // data base reference will sends data to firebase.
                databaseReference.push().setValue(user)

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
        databaseReference.addListenerForSingleValueEvent((postListener))


    }

    fun recordAudio()
    {
        if (checkPermissions() == true) {
            AudioSavaPath = (Environment.getExternalStorageDirectory().absolutePath
                    + "/" + "recordingAudio.mp3")
            mediaRecorder = MediaRecorder()
            mediaRecorder!!.setAudioSource(MediaRecorder.AudioSource.MIC)
            mediaRecorder!!.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4)
            mediaRecorder!!.setAudioEncoder(MediaRecorder.AudioEncoder.AAC)
            mediaRecorder!!.setOutputFile(AudioSavaPath)
            try {
                mediaRecorder!!.prepare()
                mediaRecorder!!.start()
                Toast.makeText(this@Audio, "Recording started", Toast.LENGTH_SHORT)
                    .show()
            } catch (e: IOException) {
                e.printStackTrace()
            }
        } else {
            ActivityCompat.requestPermissions(
                this@Audio, arrayOf(
                    Manifest.permission.RECORD_AUDIO, Manifest.permission.WRITE_EXTERNAL_STORAGE
                ), 1
            )
        }
    }

    fun stopRecord()
    {

        mediaRecorder!!.stop()
        mediaRecorder!!.release()

        Toast.makeText(this@Audio, "Recording stopped", Toast.LENGTH_SHORT).show()

    }
}