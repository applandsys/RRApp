package com.rrapp.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "installments")
data class InstallmentEntity(

    @PrimaryKey
    val id: Int,

    val installment_amount: Int,
    val due_date: String,
    val due_amount: Int,
    val secret_code: String,
    val status: String

)