package com.example.rumahnanas.ui

import android.os.Bundle
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import com.example.rumahnanas.R
import com.example.rumahnanas.data.model.Address
import com.example.rumahnanas.utils.AddressManager

class AddAddressActivity : AppCompatActivity() {
    override  fun onCreate(savedInstanceState : Bundle?){
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_address)

        val etLabel = findViewById<EditText>(R.id.etLabel)
        val etReceiverName = findViewById<EditText>(R.id.etReceiverName)
        val etPhoneNumber = findViewById<EditText>(R.id.etPhoneNumber)
        val etCompleteAddress = findViewById<EditText>(R.id.etCompleteAddress)

        val btnSave = findViewById<CardView>(R.id.btnSaveAddress)
        val btnBack = findViewById<ImageView>(R.id.btnBackAddress)

        btnBack.setOnClickListener { finish() }

        btnSave.setOnClickListener {
            val label = etLabel.text.toString().trim()
            val name = etReceiverName.text.toString().trim()
            val phone = etPhoneNumber.text.toString().trim()
            val fullAddress = etCompleteAddress.text.toString().trim()

            if (label.isEmpty() || name.isEmpty() || phone.isEmpty() || fullAddress.isEmpty()){
                Toast.makeText(this, "semua kolom wajib di isi", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val newAddress = Address(label, name, phone,fullAddress)

            AddressManager.saveAddress(this, newAddress)

            Toast.makeText(this, "Alamat Berhasil Disimpan", Toast.LENGTH_SHORT).show()
            finish()
        }
    }

}