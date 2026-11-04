package com.example.rumahnanas.ui

import android.R.attr.name
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.rumahnanas.R
import com.example.rumahnanas.databinding.ActivityCheckoutBinding
import com.example.rumahnanas.utils.AddressManager
import com.example.rumahnanas.utils.CartManager

class CheckoutActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCheckoutBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCheckoutBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val orderItems = CartManager.checkoutItems
        val totalAmount = CartManager.totalCheckoutPrice

        if (orderItems.isNotEmpty()) {

            val firstItem = orderItems[0]

            if (orderItems.size > 1) {
                binding.tvCheckoutName.text = "${firstItem.name} dan ${orderItems.size - 1} produk lainnya"
            } else {
                binding.tvCheckoutName.text = firstItem.name
            }

            val totalQty = orderItems.sumOf { it.quantity }
            binding.tvCheckoutQty.text = "x$totalQty"

            binding.tvCheckoutPrice.text = "Rp $totalAmount"
            binding.tvCheckoutTotalBar.text = "Rp $totalAmount"
            Glide.with(this)
                .load(firstItem.imageUrl)
                .placeholder(R.mipmap.ic_launcher)
                .error(R.mipmap.ic_launcher)
                .into(binding.ivCheckoutImage)

        } else {

            val name = intent.getStringExtra("CHECKOUT_NAME")?: ""
            val price = intent.getIntExtra("CHECKOUT_PRICE", 0)
            val qty = intent.getIntExtra("CHECKOUT_QTY", 1)
            val total = intent.getIntExtra("CHECKOUT_TOTAL", 0)
            val imageUrl = intent.getStringExtra("CHECKOUT_IMAGE")

            binding.tvCheckoutName.text = name
            binding.tvCheckoutPrice.text = "Rp $price"
            binding.tvCheckoutQty.text = "x$qty"
            binding.tvCheckoutTotalBar.text = "Rp $total"

            Glide.with(this)
                .load(imageUrl)
                .placeholder(R.mipmap.ic_launcher)
                .error(R.mipmap.ic_launcher)
                .into(binding.ivCheckoutImage)
        }

        binding.btnBackCheckout.setOnClickListener {
            finish()
        }

        binding.containerAddress.setOnClickListener {
            val intent = Intent(this, AddressListActivity::class.java)
            startActivity(intent)

        }

        binding.btnBuatPesanan.setOnClickListener {
            val selectedAddress = AddressManager.getSelectedAddress(this)

            if(selectedAddress == null){
                Toast.makeText(this, "Alamat Pengiriman Belum Di pilih!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val intent = Intent(this, PaymentActivity::class.java)

            val finalTotal = if (orderItems.isNotEmpty())totalAmount else getIntent().getIntExtra("CHEKOUT_TOTAL", 0)
            val finalName = if (orderItems.isNotEmpty()){
                val first = orderItems[0]
                if (orderItems.size > 1) "${first.name} dkk" else first.name
            }else getIntent().getStringExtra("CHEKOUT_NAME")?: ""

            val finalQty = if (orderItems.isNotEmpty()) orderItems.sumOf { it.quantity }else getIntent().getIntExtra("CHEKOUT_QTY", 1)

            intent.putExtra("PAYMENT_NAME", selectedAddress.receiverName)
            intent.putExtra("PAYMENT_PHONE", selectedAddress.phoneNumber)
            intent.putExtra("PAYMENT_ADDRESS", selectedAddress.completeAddress)
            intent.putExtra("PAYMENT_TOTAL", finalTotal)
            intent.putExtra("PAYMENT_PRODUCT_NAME", finalName)
            intent.putExtra("PAYMENT_PRODUCT_QTY", finalQty)

            startActivity(intent)
        }
    }

    override fun onResume(){
        super.onResume()

        val selectedAddress = AddressManager.getSelectedAddress(this)
        if (selectedAddress != null) {
            binding.tvinfoChekout.text = "${selectedAddress.receiverName} | ${selectedAddress.phoneNumber}"
            binding.tvCompleteAddress.text = selectedAddress.completeAddress
        } else {
            binding.tvinfoChekout.text = "Belum ada Alamat Dipilih!"
            binding.tvCompleteAddress.text = "Klik Di sini untuk memilih Alamat Pengiriman"
        }
    }
}