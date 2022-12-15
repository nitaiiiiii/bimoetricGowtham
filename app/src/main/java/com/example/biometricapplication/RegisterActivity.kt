package com.example.biometricapplication

import android.app.DatePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import java.util.*

class RegisterActivity : AppCompatActivity() {

    var calendarDay = 0
    var calendarMonth = 0
    var calendarYear = 0

    var edtTxtDateOfBirth :EditText? = null
    var edtTxtDateOfJoining :EditText? = null




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        edtTxtDateOfBirth = findViewById(R.id.edtTxtDateOfBirth)
        edtTxtDateOfJoining = findViewById(R.id.edtTxtDateOfJoining)


        setOnClick()
    }

    fun setOnClick(){
        edtTxtDateOfBirth?.setOnClickListener {
            getDateTimeCalender()
        }
        edtTxtDateOfJoining?.setOnClickListener {
            getDateTimeCalender()
        }
    }


    private fun getDateTimeCalender() {
        val calendar = Calendar.getInstance()
        calendarDay = calendar.get(Calendar.DAY_OF_MONTH)
        calendarMonth = calendar.get(Calendar.MONTH)
        calendarYear = calendar.get(Calendar.YEAR)

        val datePickerDialog = DatePickerDialog(this, R.style.DatePickerTheme_Dark, DatePickerDialog.OnDateSetListener { view, year, month, day ->
            val month = month + 1
            var sMonth = ""
            var sDay = ""
            sMonth = if (month < 10) {
                "0$month"
            } else {
                month.toString()
            }
            sDay = if (day < 10) {
                "0$day"
            } else {
                day.toString()
            }
            val date = "$sMonth/$sDay/$year"
            edtTxtDateOfBirth?.setText(date)
            edtTxtDateOfJoining?.setText(date)
        }, calendarYear, calendarMonth, calendarDay).let{
            it.show()
            it.setTitle("Date of Birth")
            it.setMessage("Date of Birth")
            it.datePicker.maxDate = System.currentTimeMillis()
        }
    }

}