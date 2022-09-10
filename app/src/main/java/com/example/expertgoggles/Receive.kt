package com.example.expertgoggles

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.chaquo.python.PyObject
import com.chaquo.python.Python
import com.chaquo.python.android.AndroidPlatform

class Receive : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.home_screen)

        if (! Python.isStarted()) {
            Python.start(AndroidPlatform(this));
        }

       val py : Python = Python.getInstance()
       val pyObj : PyObject = py.getModule("main2.py")

        val obj : PyObject = pyObj.callAttr("func")

        
    }

    }