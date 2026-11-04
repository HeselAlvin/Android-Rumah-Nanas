package com.example.rumahnanas.ui

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.rumahnanas.databinding.ActivitySuccesBinding
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class SuccesActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySuccesBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySuccesBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val productName = intent.getStringExtra("SUCCESS_PRODUCT_NAME") ?: "Produk Rumah Nanas"
        val totalHarga = intent.getIntExtra("SUCCESS_TOTAL", 0)
        val dateFormat = SimpleDateFormat("dd MMM yyyy, HH:mm", Locale.getDefault())
        val currentDate = dateFormat.format(Date())

        binding.tvTotalBesar.text = "Rp $totalHarga"
        binding.tvTanggal.text = currentDate
        binding.tvNamaProduk.text = productName
        binding.tvBayar.text = "Rp $totalHarga"
        binding.tvTotalkecil.text = "Rp $totalHarga"
        binding.btnBackToHome.setOnClickListener {
            val intent = Intent(this, HomeActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
        }
    }
}