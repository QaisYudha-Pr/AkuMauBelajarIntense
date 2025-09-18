package com.hai.akumaubelajarintense

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {

    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        sharedPreferences = getSharedPreferences("UserPrefs", Context.MODE_PRIVATE)

        val usernameEditText: EditText = findViewById(R.id.usernameEditText)
        val passwordEditText: EditText = findViewById(R.id.passwordEditText)
        val loginButton: Button = findViewById(R.id.loginButton)
        val registerButton: Button = findViewById(R.id.registerButton)

        registerButton.setOnClickListener {
            val username = usernameEditText.text.toString().trim()
            val password = passwordEditText.text.toString().trim()

            if (username.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Username dan Password tidak boleh kosong!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Cek apakah username sudah terdaftar
            if (sharedPreferences.contains(username)) {
                Toast.makeText(this, "Username sudah terdaftar!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Simpan username dan password
            val editor = sharedPreferences.edit()
            editor.putString(username, password)
            editor.apply()

            Toast.makeText(this, "Registrasi berhasil!", Toast.LENGTH_SHORT).show()
            usernameEditText.text.clear()
            passwordEditText.text.clear()
        }

        loginButton.setOnClickListener {
            val username = usernameEditText.text.toString().trim()
            val password = passwordEditText.text.toString().trim()

            if (username.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Username dan Password tidak boleh kosong!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Cek kredensial
            val storedPassword = sharedPreferences.getString(username, null)

            if (storedPassword != null && storedPassword == password) {
                Toast.makeText(this, "Login berhasil!", Toast.LENGTH_SHORT).show()
                val intent = Intent(this, DetailActivity::class.java).apply {
                    putExtra("EXTRA_USERNAME", username)
                }
                startActivity(intent)
                finish() // Tutup MainActivity setelah login berhasil
            } else {
                Toast.makeText(this, "Username atau Password salah!", Toast.LENGTH_SHORT).show()
            }
        }
    }
}