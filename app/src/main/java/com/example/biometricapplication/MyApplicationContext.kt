package com.example.biometricapplication

import android.app.Application
import android.content.Context

class MyApplicationContext: Application() {

    init {
        myApplicationContext = this
    }
    companion object{
        private lateinit var myApplicationContext : Application

        fun applicationContext(): Context {
            return myApplicationContext
        }

    }
}