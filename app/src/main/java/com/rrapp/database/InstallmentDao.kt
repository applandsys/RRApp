package com.rrapp.database

import androidx.room.*

@Dao
interface InstallmentDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(list: List<InstallmentEntity>)

    @Query("SELECT * FROM installments")
    suspend fun getAll(): List<InstallmentEntity>

    @Query("SELECT * FROM installments WHERE status='unpaid'")
    suspend fun getUnpaid(): List<InstallmentEntity>

}