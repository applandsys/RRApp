package com.rrapp.database

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [InstallmentEntity::class],
    version = 1
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun installmentDao(): InstallmentDao

}