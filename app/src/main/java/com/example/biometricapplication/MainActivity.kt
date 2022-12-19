package com.example.biometricapplication

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.widget.Button
import android.widget.ImageView
import android.widget.TextClock
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.concurrent.Executor

class MainActivity : AppCompatActivity() {
    lateinit var executor: Executor
    lateinit var biometricPrompt: androidx.biometric.BiometricPrompt
    lateinit var prompt: androidx.biometric.BiometricPrompt.PromptInfo
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        biometric()
        textClock()
        setOnClickListeners()

        findViewById<ImageView>(R.id.imgFingerPrint)?.setOnClickListener {
            GlobalScope.launch(Dispatchers.IO) {
                val model = BiometricDatabase.biometricRoomDB.biometricDao().selectBiometricData() as ArrayList<BiometricDataEntity>

                withContext(Dispatchers.Main) {
                    if (model.isNotEmpty()) {
                        biometricPrompt.authenticate(prompt)
                    } else {
                        val intent = Intent(this@MainActivity, RegisterActivity::class.java)
                        startActivity(intent)
                        Toast.makeText(applicationContext,"Insert New User Data...",Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
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

    }



    private fun biometric() {
        executor = ContextCompat.getMainExecutor(this)

        biometricPrompt = androidx.biometric.BiometricPrompt(this, executor, object : androidx.biometric.BiometricPrompt.AuthenticationCallback() {
            override fun onAuthenticationError(errorCode: Int, errString: CharSequence) {
                super.onAuthenticationError(errorCode, errString)
                Toast.makeText(applicationContext,"Error $errString...",Toast.LENGTH_SHORT).show()
            }

            override fun onAuthenticationFailed() {
                super.onAuthenticationFailed()
                Toast.makeText(applicationContext,"Authentication Failed...",Toast.LENGTH_SHORT).show()

            }

            override fun onAuthenticationSucceeded(result: androidx.biometric.BiometricPrompt.AuthenticationResult) {
                super.onAuthenticationSucceeded(result)
                val intent = Intent(this@MainActivity, CheckInActivity::class.java)
                startActivity(intent)
            }
        })

        prompt = androidx.biometric.BiometricPrompt.PromptInfo.Builder()
            .setTitle("Bio Authentication")
            .setSubtitle("Confirm Biometric to Save the Data...")
            .setNegativeButtonText("Cancel")
            .build()
    }



}