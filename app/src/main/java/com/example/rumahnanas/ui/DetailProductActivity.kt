package com.example.rumahnanas.ui

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.rumahnanas.R
import com.example.rumahnanas.data.model.Cart
import com.example.rumahnanas.databinding.ActivityDetailProductBinding
import com.example.rumahnanas.utils.CartManager.cartItems


class DetailProductActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailProductBinding
    private var quantity = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailProductBinding.inflate((layoutInflater))
        setContentView(binding.root)

        val productName = intent.getStringExtra("PRODUCT_NAME")
        val priceString = intent.getStringExtra("PRODUCT_PRICE") ?: "0"

        val productPrice = priceString.toDoubleOrNull()?.toInt() ?: 0

        val productDesc = intent.getStringExtra("PRODUCT_DESC")
        val productImageUrl = intent.getStringExtra("PRODUCT_IMAGE")

        binding.tvProductName.text = productName
        binding.tvProductPrice.text = "Rp $productPrice"
        binding.tvProductDesc.text = productDesc

        binding.tvProductDesc.text = productDesc?: " deskripsi tidak ditemukan di dalam database"

        val fullImageUrl = "http://10.0.2.2:8000/storage/" + productImageUrl

        Glide.with(this)
            .load(fullImageUrl)
            .placeholder(R.mipmap.ic_launcher)
            .error(R.mipmap.ic_launcher)
            .into(binding.ivProductImage)

        binding.btnBack.setOnClickListener {
            finish()
        }

        binding.btnaddCart.setOnClickListener {
            val cartItem = Cart(
                id = System.currentTimeMillis().toString(),
                name = productName ?: "produk tidak diketahui",
                Price = productPrice,
                quantity = quantity,
                imageUrl = fullImageUrl ?: ""
            )

            com.example.rumahnanas.utils.CartManager.addToCart(this, cartItem)
            Toast.makeText(this, "$quantity $productName berhasil masuk keranjang", Toast.LENGTH_SHORT).show()
        }

        binding.btnPlus.setOnClickListener {
            quantity++
            binding.tvQuantity.text = quantity.toString()

        }
        binding.btnMinus.setOnClickListener {
            if (quantity > 1) {
                quantity --
                binding.tvQuantity.text = quantity.toString()
            }else{
                Toast.makeText(this,"minimal pembelian 1 Produk", Toast.LENGTH_SHORT).show()
            }
        }

        binding.btnOrder.setOnClickListener {
            val totalharga = productPrice * quantity

            val intent = android.content.Intent(this, CheckoutActivity::class.java)

            intent.putExtra("CHECKOUT_NAME", productName)
            intent.putExtra("CHECKOUT_PRICE", productPrice)
            intent.putExtra("CHECKOUT_QTY", quantity)
            intent.putExtra("CHECKOUT_TOTAL", totalharga)
            intent.putExtra("CHECKOUT_IMAGE", fullImageUrl)

            startActivity(intent)
        }

    }
}