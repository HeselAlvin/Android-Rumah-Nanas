package com.example.rumahnanas.ui

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.rumahnanas.R
import com.example.rumahnanas.data.adapter.ProductAdapter
import com.example.rumahnanas.databinding.ActivityHomeBinding
import com.example.rumahnanas.ui.home.ProfileActivity

class HomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding
    private val viewModel: HomeViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel.products.observe(this) { productsList ->
            val adapter = ProductAdapter(productsList)
            binding.rcProducts.adapter = adapter
        }
        viewModel.error.observe(this) { errMsg ->
            Toast.makeText(this, errMsg, Toast.LENGTH_SHORT).show()
        }

        viewModel.getProducts()

        binding.bottomNav.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_home -> {
                    true
                }

                R.id.nav_cart -> {
                    val intent = Intent (this@HomeActivity, CartActivity::class.java)
                    startActivity(intent)
                    finish()
                    true
                }

                R.id.nav_me -> {
                    val intent = Intent ( this, ProfileActivity::class.java)
                    startActivity(intent)
                    finish()
                    true
                }
                else -> false

            }


        }
    }
}

