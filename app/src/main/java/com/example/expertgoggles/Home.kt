package com.example.expertgoggles

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.airbnb.lottie.LottieAnimationView
import java.util.ArrayList

class Home : AppCompatActivity() {

    lateinit var tutorialsName : String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.home_screen)

        val audio = findViewById<LottieAnimationView>(R.id.audio)
        val visual = findViewById<LottieAnimationView>(R.id.visual)
        val text = findViewById<LottieAnimationView>(R.id.text)


        val spinner = findViewById<Spinner>(R.id.selectspinner)
        val arrayList: ArrayList<String> = ArrayList()
        arrayList.add("Vikram")
        arrayList.add("Dhaiya")
        arrayList.add("Ravindra")
        arrayList.add("Kamakshi")

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

                if(tutorialsName=="Dhaiya")
                {receiveBlind()}
                else
                    if(tutorialsName=="Ravindra")
                    {receiveDumb()}
                    else
                        if(tutorialsName=="Kamakshi")
                    {}

            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }


    }

    fun receiveBlind()
    {
        val audioIntent = Intent(this, ReceiveBlind::class.java)
        startActivity(audioIntent)

    }

    fun receiveDumb()
    {
        val audioIntent = Intent(this, ReceiveDumb::class.java)
        startActivity(audioIntent)

    }

    fun audio_functionality(view : View)
    {
        val audioIntent = Intent(this, Audio::class.java)
        startActivity(audioIntent)

    }

    fun visual_functionality(view : View)
    {
        val visualIntent = Intent(this, Visual::class.java)
        startActivity(visualIntent)

    }

    fun text_functionality(view : View)
    {
        val textIntent = Intent(this, Texting::class.java)
        startActivity(textIntent)

    }

    fun githubStar(view: View) {

        val github = findViewById<ImageView>(R.id.github)
        val animation: Animation = AnimationUtils.loadAnimation(this, R.anim.bounce)
        //starts the animation
        github.startAnimation(animation)

        val uri =
            Uri.parse("http://github.com/Philotes-exceptus/Whooby") // missing 'http://' will cause crashed

        val intent = Intent(Intent.ACTION_VIEW, uri)
        startActivity(intent)

    }



}