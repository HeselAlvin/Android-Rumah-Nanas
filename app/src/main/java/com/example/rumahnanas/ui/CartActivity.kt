package com.example.rumahnanas.ui

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.rumahnanas.data.adapter.CartAdapter
import com.example.rumahnanas.data.model.Cart
import com.example.rumahnanas.databinding.ActivityCartBinding


class CartActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCartBinding
    private lateinit var cartAdapter: CartAdapter
    private var cartList = com.example.rumahnanas.utils.CartManager.cartItems

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCartBinding.inflate(layoutInflater)
        setContentView(binding.root)

        com.example.rumahnanas.utils.CartManager.loadCart(this)


        cartAdapter = CartAdapter(cartList) {
            updateTotalPrice()
        }

        binding.rvCartItems.layoutManager = LinearLayoutManager(this)
        binding.rvCartItems.adapter = cartAdapter

        updateTotalPrice()

        binding.btnBack.setOnClickListener {
            val intent = Intent(this@CartActivity, HomeActivity::class.java)
            startActivity(intent)
            finish()
        }

        binding.btnCheckout.setOnClickListener {
            val selectedItems = cartList.filter { it.isSelected }
            if (selectedItems.isNotEmpty()){
                com.example.rumahnanas.utils.CartManager.checkoutItems =selectedItems.toMutableList()
                val totalHarga = selectedItems.sumOf { it.Price * it.quantity }
                com.example.rumahnanas.utils.CartManager.totalCheckoutPrice = totalHarga
                val intent =android.content.Intent(this, CheckoutActivity::class.java)
                startActivity(intent)
            }else{
                android.widget.Toast.makeText(this, "Belum memilih item", android.widget.Toast.LENGTH_SHORT).show()
            }
        }
    }


    private fun updateTotalPrice() {
        var totalPrice = 0
        var totalItem = 0

        for (item in cartList) {
            if (item.isSelected) {
                    totalPrice += (item.Price * item.quantity)
                    totalItem += item.quantity
                }
            }
            binding.tvTotalPrice.text = "Rp $totalPrice"
            binding.btnCheckout.text = "Checkout ($totalItem)"
        }
    }
