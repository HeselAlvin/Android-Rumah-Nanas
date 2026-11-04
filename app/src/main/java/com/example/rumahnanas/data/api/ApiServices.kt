package com.example.rumahnanas.data.api

import com.example.rumahnanas.data.model.LoginRequest
import com.example.rumahnanas.data.model.LoginResponse
import com.example.rumahnanas.data.model.ProductResponse
import com.example.rumahnanas.data.model.RegisterRequest
import com.example.rumahnanas.data.model.RegisterResponse
import com.example.rumahnanas.data.model.VerifyOtpRequest
import com.example.rumahnanas.data.model.VerifyOtpResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface ApiServices {
    @POST("login")
    suspend fun  loginUser(
        @Body request: LoginRequest
    ): Response<LoginResponse>
    @Headers("Accept: application/json")
    @POST("register")
    suspend fun register(
        @Body request: RegisterRequest
    ): Response<RegisterResponse>

    @POST("api/verify-otp")
    fun verifyOtp(@Body request: VerifyOtpRequest
    ): Response<VerifyOtpResponse>

    @Headers("Accept: application/json")
    @GET("products")
    suspend fun getProducts(): Response<ProductResponse>

    @Headers("Accept: application/json")
    @Multipart
    @POST("orders")
    fun uploadOrder(
        @Part("customer_name") name: RequestBody,
        @Part("customer_phone") phone: RequestBody,
        @Part("address") address: RequestBody,
        @Part("product_name") productName: RequestBody,
        @Part("quantity") qty: RequestBody,
        @Part("total_price") totalPrice: RequestBody,
        @Part payment_receipt: MultipartBody.Part
        ): Call<Any>
}