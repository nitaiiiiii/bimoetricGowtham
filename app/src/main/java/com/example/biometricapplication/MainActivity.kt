package com.example.biometricapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextClock

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        textClock()

    }

    fun textClock(){
        val clockTC = findViewById<TextClock>(R.id.idTCClock)
        clockTC.format24Hour = "hh:mm:ss a"
    }


}