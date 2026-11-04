package com.example.rumahnanas.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.rumahnanas.data.api.ApiClient
import com.example.rumahnanas.data.model.LoginRequest
import com.example.rumahnanas.data.model.LoginResponse
import com.example.rumahnanas.data.model.RegisterRequest
import com.example.rumahnanas.data.model.RegisterResponse
import com.example.rumahnanas.data.model.VerifyOtpRequest
import com.example.rumahnanas.data.model.VerifyOtpResponse
import kotlinx.coroutines.launch

class AuthViewModel : ViewModel() {

    private val _loginSuccess = MutableLiveData <LoginResponse>()
    val loginSuccess: LiveData<LoginResponse> = _loginSuccess

    private val _loginError = MutableLiveData<String>()
    val loginError: LiveData<String> = _loginError

    private val _registerResponse = MutableLiveData<RegisterResponse?>()
    val registerResponse: LiveData<RegisterResponse?> = _registerResponse

    private val _verifyOtpResponse = MutableLiveData<VerifyOtpResponse?>()
    val  VerifyOtpResponse: LiveData<VerifyOtpResponse?> = _verifyOtpResponse

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> = _error

    fun login(email: String, password: String) {
        viewModelScope.launch {
            try {
                val request = LoginRequest(email, password)
                val response = ApiClient.instance.loginUser(request)

                if (response.isSuccessful && response.body() != null) {
                    _loginSuccess.value = response.body()
                } else {
                    _loginError.value = "Email atau Password Salah!"
                }

            } catch (e: Exception){
                _loginError.value = " GAGAL konek ke server: ${e.message} "
            }
        }
    }

    fun register(request: RegisterRequest){
        viewModelScope.launch {
            try {
                val response = ApiClient.instance.register(request)
                if (response.isSuccessful && response.body() != null){
                    _registerResponse.value = response.body()
                }else{
                    _error.value = "Gagal Mendaftar, Silahkan di ulangi Lagi!: ${response.message()}"
                }
            }catch (e: Exception){
                _error.value = "koneksi Bermasalah: ${e.message}"
            }
        }
    }

    fun verifyOtp(request: VerifyOtpRequest){
        viewModelScope.launch {
            try {
                val response = ApiClient.instance.verifyOtp(request)

                if (response.isSuccessful && response.body() !=null){
                    _verifyOtpResponse.value = response.body()
                }else{
                    _error.value = "Verifikasi Gagal: ${response.message()}"
                    _verifyOtpResponse.value = null
                }
            }catch (e: Exception){
                _error.value = "Koneksi Bermasalah: ${e.message}"
                _verifyOtpResponse.value = null
            }
        }
    }
}