package com.rrapp.repository

import com.rrapp.database.InstallmentDao
import com.rrapp.database.InstallmentEntity
import com.rrapp.model.ApiResponse

class InstallmentRepository(
    private val dao: InstallmentDao
) {

    suspend fun saveApiData(response: ApiResponse) {

        val list = response.installments.map {

            InstallmentEntity(
                it.id,
                it.installment_amount,
                it.due_date,
                it.due_amount,
                it.secret_code,
                it.status
            )

        }

        dao.insertAll(list)

    }

}