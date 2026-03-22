package com.rrapp.model

data class ApiResponse(

    val customer: Customer,
    val order: Order,
    val installments: List<Installment>,
    val deviceAdminStatus: Boolean,
    val deviceRemoveStatus: Boolean

)

data class Customer(
    val id: Int,
    val name: String,
    val phone: String
)

data class Order(
    val customer_id: Int,
    val total_amount: Int,
    val paid_amount: Int,
    val due_amount: Int,
    val status: String
)

data class Installment(
    val id: Int,
    val installment_amount: Int,
    val due_date: String,
    val due_amount: Int,
    val secret_code: String,
    val status: String
)