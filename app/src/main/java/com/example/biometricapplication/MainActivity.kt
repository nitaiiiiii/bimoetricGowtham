package com.example.biometricapplication

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.widget.Button
import android.widget.ImageView
import android.widget.TextClock
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        textClock()
        setOnClickListeners()


    }

    fun textClock() {
        val clockTC = findViewById<TextClock>(R.id.idTCClock)
        clockTC.format24Hour = "hh:mm:ss a"
    }

    fun setOnClickListeners() {
        val btnRegister = findViewById<Button>(R.id.btnDataRegistration)
        val btnCheckStatus = findViewById<Button>(R.id.btnCheckStatus)
        val imgFingerprint = findViewById<ImageView>(R.id.imgFingerPrint)

        btnRegister.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }
        btnCheckStatus.setOnClickListener {
            val intent = Intent(this, CheckStatusActivity::class.java)
            startActivity(intent)
        }
        imgFingerprint.setOnClickListener {
            val intent = Intent(this, CheckInActivity::class.java)
            startActivity(intent)
        }
    }


}