package com.example.expertgoggles

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.airbnb.lottie.LottieAnimationView

class Home : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.home_screen)

        val audio = findViewById<LottieAnimationView>(R.id.audio)
        val visual = findViewById<LottieAnimationView>(R.id.visual)
        val text = findViewById<LottieAnimationView>(R.id.text)


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