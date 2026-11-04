package com.example.rumahnanas.ui

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.OpenableColumns
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.example.rumahnanas.data.api.ApiClient
import com.example.rumahnanas.databinding.ActivityPaymentBinding

class PaymentActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPaymentBinding
    private var imageUri: Uri? = null

    private val pickImage =
        registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
            if (uri != null) {
                imageUri = uri

                val cursor = contentResolver.query(uri, null, null, null, null)
                cursor?.use {
                    if (it.moveToFirst()) {
                        val displayNameIndex = it.getColumnIndex(OpenableColumns.DISPLAY_NAME)
                        if (displayNameIndex != -1) {
                            val fileName = it.getString(displayNameIndex)
                            binding.tvFileName.text = fileName
                            binding.tvFileName.setTextColor(android.graphics.Color.BLACK)
                        }
                    }
                }
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPaymentBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val customerName = intent.getStringExtra("PAYMENT_NAME") ?: "Pembeli"
        val customerPhone = intent.getStringExtra("PAYMENT_PHONE") ?: "000"
        val customerAddress = intent.getStringExtra("PAYMENT_ADDRESS") ?: "Alamat Kosong"
        val productName = intent.getStringExtra("PAYMENT_PRODUCT_NAME") ?: "Produk"
        val productQty = intent.getIntExtra("PAYMENT_PRODUCT_QTY", 1)
        val totalHarga = intent.getIntExtra("PAYMENT_TOTAL", 0)

        binding.tvPaymentTotal.text = "Rp $totalHarga"

        binding.btnBackPayment.setOnClickListener {
            finish()
        }

        binding.btnBrowse.setOnClickListener {
            pickImage.launch("image/*")
        }

        binding.btnSelesaiPayment.setOnClickListener {
            if (imageUri == null) {
                Toast.makeText(this, "Silahkan isi Bukti Pembayaran terlebih Dahulu!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            Toast.makeText(this, "Sedang mengupload ke server...", Toast.LENGTH_LONG).show()

            val file = uriToFile(imageUri!!)

            val requestImageFile = okhttp3.RequestBody.create(okhttp3.MediaType.parse("image/jpeg"), file)
            val imageMultipart = okhttp3.MultipartBody.Part.createFormData("payment_receipt", file.name, requestImageFile)
            val namePart = okhttp3.RequestBody.create(okhttp3.MediaType.parse("text/plain"), customerName)
            val phonePart = okhttp3.RequestBody.create(okhttp3.MediaType.parse("text/plain"), customerPhone)
            val addressPart = okhttp3.RequestBody.create(okhttp3.MediaType.parse("text/plain"), customerAddress)
            val productPart = okhttp3.RequestBody.create(okhttp3.MediaType.parse("text/plain"), productName)
            val qtyPart = okhttp3.RequestBody.create(okhttp3.MediaType.parse("text/plain"), productQty.toString())
            val totalPart = okhttp3.RequestBody.create(okhttp3.MediaType.parse("text/plain"), totalHarga.toString())

            ApiClient.instance.uploadOrder(
                namePart,
                phonePart,
                addressPart,
                productPart,
                qtyPart,
                totalPart,
                imageMultipart
            ).enqueue(object : retrofit2.Callback<Any> {
                override fun onResponse(call: retrofit2.Call<Any>, response: retrofit2.Response<Any>) {
                    if (response.isSuccessful) {
                        Toast.makeText(this@PaymentActivity, "Pesanan Berhasil Dibuat!", Toast.LENGTH_LONG).show()
                        val intent = Intent(this@PaymentActivity, SuccesActivity::class.java)
                        intent.putExtra("SUCCESS_PRODUCT_NAME", productName)
                        intent.putExtra("SUCCESS_TOTAL", totalHarga)

                        startActivity(intent)
                        finish()

                    } else {
                        Toast.makeText(this@PaymentActivity, "Gagal upload: ${response.message()}", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: retrofit2.Call<Any>, t: Throwable) {
                    Toast.makeText(this@PaymentActivity, "Error Jaringan: ${t.message}", Toast.LENGTH_SHORT).show()
                }
            })
        }
    }

    private fun uriToFile(selectedImg: android.net.Uri): java.io.File{
        val contentResolver = contentResolver
        val myFile = java.io.File(cacheDir, "bukti_transfer_${System.currentTimeMillis()}.jpg")

        val inputStream = contentResolver.openInputStream(selectedImg)
        val outputStream = java.io.FileOutputStream(myFile)

        inputStream?.copyTo(outputStream)
        outputStream.close()
        inputStream?.close()

        return myFile
    }
}