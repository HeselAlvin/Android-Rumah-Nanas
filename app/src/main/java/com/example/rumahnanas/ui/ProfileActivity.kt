package com.example.rumahnanas.ui.home

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts.PickVisualMedia
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.rumahnanas.R
import com.example.rumahnanas.databinding.ActivityProfileBinding
import com.example.rumahnanas.ui.HomeActivity

class ProfileActivity : AppCompatActivity() {

    private lateinit var binding: ActivityProfileBinding
    private lateinit var sharedPreferences: android.content.SharedPreferences

    private val pickMedia = registerForActivityResult(PickVisualMedia()) { uri ->
        if (uri != null) {
            Glide.with(this)
                .load(uri)
                .into(binding.ivProfileImage)

            saveProfileImageUri(uri)
        } else {
            Toast.makeText(this, "Tidak ada foto yang dipilih", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        sharedPreferences = getSharedPreferences("RUMAH_NANAS_PREF", Context.MODE_PRIVATE)

        loadSavedProfileImage()

        binding.bottomnavProfile.selectedItemId = R.id.nav_me

        binding.bottomnavProfile.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_home -> {

                    val intent = Intent(this, HomeActivity::class.java)
                    startActivity(intent)
                    finish()
                    true
                }
                R.id.nav_cart -> {
                    Toast.makeText(this, "Halaman Keranjang!", Toast.LENGTH_SHORT).show()
                    true
                }
                R.id.nav_me -> {
                    true
                }
                else -> false
            }
        }

        binding.ivProfileImage.setOnClickListener {

            pickMedia.launch(PickVisualMediaRequest(PickVisualMedia.ImageOnly))
        }
    }

    private fun saveProfileImageUri(uri: Uri) {
        val editor = sharedPreferences.edit()
        editor.putString("USER_AVATAR_URI", uri.toString())
        editor.apply()
    }

    private fun loadSavedProfileImage() {
        val savedUriStr = sharedPreferences.getString("USER_AVATAR_URI", null)
        if (savedUriStr != null) {
            val uri = Uri.parse(savedUriStr)
            Glide.with(this)
                .load(uri)
                .into(binding.ivProfileImage)
        }
    }
}