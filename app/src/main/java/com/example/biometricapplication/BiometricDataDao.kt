package com.example.biometricapplication

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface BiometricDataDao {

    @Insert
    fun insertBiometricData(biometricDataEntity: BiometricDataEntity)

    @Query("SELECT * FROM BiometricDataEntity")
    fun selectBiometricData() : List<BiometricDataEntity>
}