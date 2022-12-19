package com.example.biometricapplication

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity
class BiometricDataEntity(
    @PrimaryKey(autoGenerate = false)
    val Name: String,
    val EmployeeID: String,
    val DateOfBirth: String,
    val DateOfJoining: String,
    val MobileNumber: String,
    val Team: String

): Serializable