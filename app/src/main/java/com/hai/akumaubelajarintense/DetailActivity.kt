package com.hai.akumaubelajarintense

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class DetailActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        val username = intent.getStringExtra("EXTRA_USERNAME") // Terima username dari Intent
        val detailTextView: TextView = findViewById(R.id.detailTextView)
        val headerTextView: TextView = findViewById(R.id.headerTextView) // Referensi ke header

        if (username != null && username.isNotEmpty()) {
            headerTextView.text = "Login Berhasil!"
            detailTextView.text = "Selamat datang di aplikasi, $username!\n\nIni adalah halaman setelah Anda berhasil login atau mendaftar."
        } else {
            headerTextView.text = "Akses Ditolak"
            detailTextView.text = "Username tidak boleh kosong. Silakan kembali dan coba lagi."
        }
    }
}