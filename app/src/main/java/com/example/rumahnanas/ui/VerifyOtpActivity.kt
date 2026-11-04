package com.example.rumahnanas.ui

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.rumahnanas.data.model.VerifyOtpRequest
import com.example.rumahnanas.databinding.ActivityVerifyOtpBinding

class VerifyOtpActivity : AppCompatActivity() {

    private lateinit var binding: ActivityVerifyOtpBinding
    private val viewModel: AuthViewModel by viewModels()
    private var registeredEmail: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityVerifyOtpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        registeredEmail = intent.getStringExtra("EXTRA_EMAIL") ?: ""

        viewModel.VerifyOtpResponse.observe(this) { response ->
            if (response != null) {
                Toast.makeText(this, "Verifikasi Berhasil! Silakan Login.", Toast.LENGTH_SHORT).show()

                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                finishAffinity()
            } else {
                Toast.makeText(this, "Kode OTP salah atau kedaluwarsa!", Toast.LENGTH_SHORT).show()
            }
        }

        binding.btnVerify.setOnClickListener {
            val otpCode = binding.etOtpCode.text.toString().trim()

            if (otpCode.isEmpty()) {
                Toast.makeText(this, "Masukkan kode OTP!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val request = VerifyOtpRequest(registeredEmail, otpCode)
            viewModel.verifyOtp(request)
        }
    }
}