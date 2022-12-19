package com.example.biometricapplication

import android.app.DatePickerDialog
import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Spinner
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.room.Entity
import java.util.*
import java.util.concurrent.Executor

class RegisterActivity : AppCompatActivity() {

    var calendarDay = 0
    var calendarMonth = 0
    var calendarYear = 0

    var edtTxtDateOfBirth: EditText? = null
    var edtTxtDateOfJoining: EditText? = null
    var btnRegisterSubmit: Button? = null

    lateinit var executor: Executor
    lateinit var biometricDataEntity: BiometricDataEntity
    lateinit var biometricPrompt: androidx.biometric.BiometricPrompt
    lateinit var prompt: androidx.biometric.BiometricPrompt.PromptInfo


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        edtTxtDateOfBirth = findViewById(R.id.edtTxtDateOfBirth)
        edtTxtDateOfJoining = findViewById(R.id.edtTxtDateOfJoining)
        btnRegisterSubmit = findViewById(R.id.btnRegisterSubmit)

        setOnClick()
        displayDialogPopup()
        insertData()

    }

    private fun displayDialogPopup() {
        val popupDialog = Dialog(this)
        popupDialog.setCancelable(false)

        popupDialog.setContentView(R.layout.popup)
        popupDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        val save = popupDialog.findViewById<Button>(R.id.btnSave)

        btnRegisterSubmit?.setOnClickListener {

                popupDialog.show()

        }

        save.setOnClickListener {
            popupDialog.dismiss()
        }


    }

    fun setOnClick() {
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

        val datePickerDialog = DatePickerDialog(
            this,
            R.style.DatePickerTheme_Dark,
            DatePickerDialog.OnDateSetListener { view, year, month, day ->
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
            },
            calendarYear,
            calendarMonth,
            calendarDay
        ).let {
            it.show()
            it.setTitle("Date of Birth")
            it.setMessage("Date of Birth")
            it.datePicker.maxDate = System.currentTimeMillis()
        }
    }

    private fun insertData() {
        val edtName = findViewById<EditText>(R.id.edtTxtName)
        val employeeID = findViewById<EditText>(R.id.edtTxtEmployeeID)
        val dateOfBirth = findViewById<EditText>(R.id.edtTxtDateOfBirth)
        val dateOfJoining = findViewById<EditText>(R.id.edtTxtDateOfJoining)
        val mobileNumber = findViewById<EditText>(R.id.edtTxtMobileNumber)
        val team = findViewById<EditText>(R.id.edtTxtTeam)

        val popupDialog = Dialog(this)
        popupDialog.setCancelable(false)

        popupDialog.setContentView(R.layout.popup)
        popupDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        val fingerPrint = popupDialog.findViewById<ImageView>(R.id.imgFinger)

        fingerPrint.setOnClickListener {
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

                    biometricDataEntity = BiometricDataEntity(
                        edtName?.text?.trim()?.toString().toString(),
                        employeeID?.text?.trim()?.toString().toString(),
                        dateOfBirth?.text?.trim()?.toString().toString(),
                        dateOfJoining?.text?.trim()?.toString().toString(),
                        mobileNumber?.text?.filter { number -> number.isDigit() }.toString(),
                        team?.text?.trim()?.toString().toString()
                    )

                    BiometricDatabase.biometricRoomDB.biometricDao().insertBiometricData(biometricDataEntity)

                    val intent = Intent(this@RegisterActivity, MainActivity::class.java)
                    startActivity(intent)
                }
            })

            prompt = androidx.biometric.BiometricPrompt.PromptInfo.Builder()
                .setTitle("Bio Authentication")
                .setSubtitle("Confirm Biometric to Save the Data...")
                .setNegativeButtonText("Cancel")
                .build()

            biometricPrompt.authenticate(prompt)
        }


        btnRegisterSubmit?.setOnClickListener {
            if(nameCheck() && employeeIDCheck() && dateOfBirthCheck() && joiningCheck() && mobileNumberCheck() && teamCheck()){
                val biometricDataEntity = BiometricDataEntity(
                    edtName.text.toString(),
                    employeeID.text.toString(),
                    dateOfBirth.text.toString(),
                    mobileNumber.text.toString(),
                    dateOfJoining.text.toString(),
                    team.text.toString()
                )

                popupDialog.show()

//                BiometricDatabase.biometricRoomDB.biometricDao().insertBiometricData(biometricDataEntity)
//                Toast.makeText(this, "Data Inserted Successfully", Toast.LENGTH_SHORT).show()

            }else{
            }

        }

    }

    private fun nameCheck(): Boolean{
        val edtTxtName = findViewById<EditText>(R.id.edtTxtName)

        val nameCheck = edtTxtName
        return if(nameCheck?.text?.length!=0){
            true
        }else{
            edtTxtName.error = "Name is Required"
            edtTxtName.requestFocus()
            false
        }


    }
    private fun employeeIDCheck(): Boolean{
        val employeeID = findViewById<EditText>(R.id.edtTxtEmployeeID)

        val employeeCheck = employeeID
        return if(employeeCheck?.text?.length!=0){
            true
        }else{
            employeeID.error = "EmployeeID is Required"
            employeeID.requestFocus()
            false
        }


    }
    private fun dateOfBirthCheck(): Boolean{
        val dateOfBirth = findViewById<EditText>(R.id.edtTxtDateOfBirth)

        val birthCheck = dateOfBirth
        return if(birthCheck?.text?.length!=0){
            true
        }else{
            dateOfBirth.error = "edtDateOfBirth is Required"
            dateOfBirth.requestFocus()
            false
        }


    }
    private fun joiningCheck(): Boolean{
        val dateOfJoining = findViewById<EditText>(R.id.edtTxtDateOfJoining)

        val joiningCheck = dateOfJoining
        return if(joiningCheck?.text?.length!=0){
            true
        }else{
            dateOfJoining.error = "edtDateOfJoining is Required"
            dateOfJoining.requestFocus()
            false
        }


    }
    private fun mobileNumberCheck(): Boolean{
        val mobileNumber = findViewById<EditText>(R.id.edtTxtMobileNumber)

        val relievingCheck = mobileNumber
        return if(relievingCheck?.text?.length == 10){
            true
        }else{
            mobileNumber.error = "mobile number is Required"
            mobileNumber.requestFocus()
            false
        }


    }
    private fun teamCheck(): Boolean{
        val team = findViewById<EditText>(R.id.edtTxtTeam)

        val yearsCheck = team
        return if(yearsCheck?.text?.length!=0){
            true
        }else{
            team.error = "team name is Required"
            team.requestFocus()
            false
        }


    }

}