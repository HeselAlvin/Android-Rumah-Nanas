package com.example.rumahnanas.ui

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.rumahnanas.databinding.ActivityMainBinding



class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val viewModel: AuthViewModel by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel.loginSuccess.observe(this) { response ->
            Toast.makeText(this,"berhasil! Halo ${response.user.name}", Toast.LENGTH_LONG).show()
            val  intent = android.content.Intent(this, HomeActivity::class.java)
            startActivity(intent)
            finish()
        }

            viewModel.loginError.observe(this) { errorMesage ->
                Toast.makeText(this, errorMesage, Toast.LENGTH_LONG).show()

            }

        binding.btnLogin.setOnClickListener {
            val email = binding.etUsername.text.toString().trim()
            val password = binding.etPassword.text.toString().trim()

            if (email.isNotEmpty() && password.isNotEmpty()){
                viewModel.login(email, password)
            }else  {
                Toast.makeText(this, "Semua field wajib diisi", Toast.LENGTH_SHORT).show()
            }
        }

            binding.tvSignUp.setOnClickListener {
                val intent = Intent( this, RegisterActivity::class.java)
                startActivity(intent)
            }

        }
    }
