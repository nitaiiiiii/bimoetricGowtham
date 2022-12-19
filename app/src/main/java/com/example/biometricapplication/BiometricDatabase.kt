package com.example.biometricapplication

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [BiometricDataEntity::class], version = 1, exportSchema = false)
abstract class BiometricDatabase : RoomDatabase() {
    abstract fun biometricDao(): BiometricDataDao

    companion object {
        val biometricRoomDB =
            Room.databaseBuilder(
                MyApplicationContext.applicationContext(),
                BiometricDatabase::class.java,
                "biometricData"
            ).fallbackToDestructiveMigration().allowMainThreadQueries().build()
    }


}