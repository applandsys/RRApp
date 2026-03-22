package com.rrapp.network

import com.rrapp.model.ApiResponse
import retrofit2.http.Body
import retrofit2.http.POST

data class LicenseRequest(
    val license_key: String
)

interface ApiService {

    @POST("api/customer/check-license")
    suspend fun checkLicense(
        @Body request: LicenseRequest
    ): ApiResponse

}