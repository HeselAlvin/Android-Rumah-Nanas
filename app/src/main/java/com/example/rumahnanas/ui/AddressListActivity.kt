package com.example.rumahnanas.ui

import android.content.Intent
import android.os.Bundle
import android.provider.Telephony
import android.widget.Button
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.rumahnanas.R
import com.example.rumahnanas.data.adapter.AddressAdapter
import com.example.rumahnanas.data.model.Address
import com.example.rumahnanas.utils.AddressManager

class AddressListActivity : AppCompatActivity() {
    private lateinit var  rvAddress: RecyclerView
    private lateinit var  adapter: AddressAdapter

    override fun onCreate(savedInstance: Bundle?){
        super.onCreate(savedInstance)
        setContentView(R.layout.activity_address_list)

        val btnBack = findViewById<ImageView>(R.id.btnBackAddressList)
        val btnAdd = findViewById<Button>(R.id.btnAddNewAddress)
        rvAddress = findViewById(R.id.rvAddressList)

        btnBack.setOnClickListener {finish()}

        btnAdd.setOnClickListener {
            startActivity(Intent(this, AddAddressActivity::class.java))
        }
        setupRecyclerView()
    }
    override fun onResume(){
        super.onResume()
        adapter.updateData(AddressManager.getAddressList(this))
    }

    private fun  setupRecyclerView(){
        val initialList = AddressManager.getAddressList(this)

        adapter = AddressAdapter(initialList) {selectedAddress ->
            AddressManager.saveSelectedAddress(this, selectedAddress)
            finish()
        }

        rvAddress.layoutManager = LinearLayoutManager(this)
        rvAddress.adapter = adapter
    }
}