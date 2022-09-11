package com.example.expertgoggles

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class splashscreen : AppCompatActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.splash)

        val sIntent = Intent(this, Home::class.java)
        startActivity(sIntent)
    }
}