package com.example.biometricapplication

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.style.ForegroundColorSpan
import android.util.Log
import android.widget.TextView
import androidx.core.content.ContextCompat
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class CheckInActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_check_in)
        Handler(Looper.getMainLooper()).postDelayed({ this@CheckInActivity.finish() }, 3000)
        setTextViews()
    }

    @SuppressLint("SimpleDateFormat")
    private fun setTextViews() {
        GlobalScope.launch(Dispatchers.IO) {
            val model = BiometricDatabase.biometricRoomDB.biometricDao().selectBiometricData() as ArrayList<BiometricDataEntity>

            withContext(Dispatchers.Main) {
                val sdf = SimpleDateFormat("HH:mm")
                val currentTime: String = sdf.format(Date())
                Log.e("currentTime", "$currentTime")
                var wishString = "Good Morning, Welcome "

                val date1 = sdf.parse(currentTime)
                val date2 = sdf.parse("9:00")
                val date3 = sdf.parse("13:00")
                val date4 = sdf.parse("14:00")
                val date5 = sdf.parse("18:00")
                val date6 = sdf.parse("20:00")

                try {
                    if (date1 != null) {
                        if (date1.after(date2) && date1.before(date3)) {
                            wishString = "Good Morning, Welcome "

                            findViewById<TextView>(R.id.tvStatus).text = "Check - In"
                            findViewById<TextView>(R.id.tvEmployeeId).text = model[0].EmployeeID
                            findViewById<TextView>(R.id.tvTime).text = currentTime.toString()
                        } else if (date1.after(date3) && date1.before(date4)) {
                            wishString = "Have a great Lunch ,"

                            findViewById<TextView>(R.id.tvStatus).text = "LUNCH"
                            findViewById<TextView>(R.id.tvEmployeeId).text = model[0].EmployeeID
                            findViewById<TextView>(R.id.tvTime).text = currentTime.toString()
                        } else if (date1.after(date4) && date1.before(date5)) {
                            wishString = "Welcome Back To Work, "

                            findViewById<TextView>(R.id.tvStatus).text = "WORKING"
                            findViewById<TextView>(R.id.tvEmployeeId).text = model[0].EmployeeID
                            findViewById<TextView>(R.id.tvTime).text = currentTime.toString()
                        } else if (date1.after(date5) && date1.before(date6)) {
                            wishString = "Thank You, "

                            findViewById<TextView>(R.id.tvStatus).text = "CHECK-OUT"
                            findViewById<TextView>(R.id.tvEmployeeId).text = model[0].EmployeeID
                            findViewById<TextView>(R.id.tvTime).text = currentTime.toString()
                        }else {
                            wishString = "Office Closed, "

                            findViewById<TextView>(R.id.tvStatus).text = "--"
                            findViewById<TextView>(R.id.tvEmployeeId).text = model[0].EmployeeID
                            findViewById<TextView>(R.id.tvTime).text = currentTime.toString()
                        }
                    }
                } catch (e: ParseException) {
                    e.printStackTrace()
                }
                val nameString = model[0].Name
                spanStringBuilder(findViewById<TextView>(R.id.txtCheckInWelcome)!!, wishString + nameString, wishString.length, wishString.length + nameString.length)
            }
          }
    }

    /**
     *  to change selected text style
     */
    private fun spanStringBuilder(textView: TextView, message: String, start: Int, end: Int) {
        val spannableString = SpannableStringBuilder(message)
        val fColor = ForegroundColorSpan(ContextCompat.getColor(this, R.color.mainColor))
        spannableString.setSpan(fColor, start, end, Spannable.SPAN_INCLUSIVE_EXCLUSIVE)
        textView.text = spannableString
    }
}