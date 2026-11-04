package com.example.rumahnanas.ui

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.rumahnanas.data.model.RegisterRequest
import com.example.rumahnanas.data.model.VerifyOtpResponse
import com.example.rumahnanas.databinding.ActivityRegisterBinding

class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding
    private val viewModel: AuthViewModel by viewModels()

    override fun onCreate(savedInstance: Bundle?){
        super.onCreate(savedInstance)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel.registerResponse.observe(this ){response ->
            if (response != null){
                Toast.makeText(this, "Silahkan cek email untuk verifikasi", Toast.LENGTH_SHORT).show()

                val intent = Intent(this, VerifyOtpActivity::class.java)
                intent.putExtra("EXTRA_EMAIL", response.email ?: binding.etEmailRegister.text.toString().trim())
                startActivity(intent)

                finish()
            }
        }

        viewModel.error.observe(this) { errorMessage ->
            if (errorMessage != null) {
                Toast.makeText(this, errorMessage, Toast.LENGTH_LONG).show()
            }
        }

        binding.btnRegister.setOnClickListener {
            val name = binding.etNameregister.text.toString().trim()
            val email = binding.etEmailRegister.text.toString().trim()
            val pass = binding.etPasswordregister.text.toString().trim()
            val confirm = binding.etconfirmPasswor.text.toString().trim()

            if (name.isEmpty() || email.isEmpty() || pass.isEmpty()){
                Toast.makeText(this, "semua field wajib diisi!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if (pass != confirm){
                Toast.makeText(this,"password tidak sama!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val request = RegisterRequest(name, email, pass, confirm)
            viewModel.register(request)
        }
    }


}