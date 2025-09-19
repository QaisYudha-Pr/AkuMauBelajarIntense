package com.hai.akumaubelajarintense

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class ChangeCredentialsActivity : AppCompatActivity() {

    private lateinit var currentUsernameTextView: TextView
    private lateinit var newUsernameEditText: EditText
    private lateinit var currentPasswordEditText: EditText
    private lateinit var newPasswordEditText: EditText
    private lateinit var confirmNewPasswordEditText: EditText
    private lateinit var saveChangesButton: Button
    private lateinit var sharedPreferences: SharedPreferences

    private var originalUsername: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_change_credentials)

        val rootView = findViewById<View>(android.R.id.content).rootView
        ViewCompat.setOnApplyWindowInsetsListener(rootView) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        currentUsernameTextView = findViewById(R.id.currentUsernameTextView)
        newUsernameEditText = findViewById(R.id.newUsernameEditText)
        currentPasswordEditText = findViewById(R.id.currentPasswordEditText)
        newPasswordEditText = findViewById(R.id.newPasswordEditText)
        confirmNewPasswordEditText = findViewById(R.id.confirmNewPasswordEditText)
        saveChangesButton = findViewById(R.id.saveChangesButton)

        sharedPreferences = getSharedPreferences("UserPrefs", Context.MODE_PRIVATE)
        originalUsername = intent.getStringExtra("EXTRA_USERNAME")

        if (originalUsername != null) {
            currentUsernameTextView.text = "Mengubah kredensial untuk: $originalUsername"
        } else {
            currentUsernameTextView.text = "Username tidak ditemukan."
            saveChangesButton.isEnabled = false
            newUsernameEditText.isEnabled = false
            currentPasswordEditText.isEnabled = false
            newPasswordEditText.isEnabled = false
            confirmNewPasswordEditText.isEnabled = false
        }

        saveChangesButton.setOnClickListener {
            handleChangeCredentials()
        }
    }

    private fun handleChangeCredentials() {
        if (originalUsername == null) {
            Toast.makeText(this, "Tidak dapat mengubah kredensial, username asli tidak diketahui.", Toast.LENGTH_LONG).show()
            return
        }

        val enteredCurrentPassword = currentPasswordEditText.text.toString().trim()
        val newUsername = newUsernameEditText.text.toString().trim()
        val newPassword = newPasswordEditText.text.toString().trim()
        val confirmNewPassword = confirmNewPasswordEditText.text.toString().trim()

        if (enteredCurrentPassword.isEmpty()) {
            currentPasswordEditText.error = "Password saat ini tidak boleh kosong!"
            Toast.makeText(this, "Password saat ini tidak boleh kosong!", Toast.LENGTH_SHORT).show()
            return
        }

        val storedPassword = sharedPreferences.getString(originalUsername, null)
        if (storedPassword != enteredCurrentPassword) {
            currentPasswordEditText.error = "Password saat ini salah!"
            Toast.makeText(this, "Password saat ini salah!", Toast.LENGTH_SHORT).show()
            return
        }

        // Validasi untuk username baru
        var finalUsername = originalUsername!!
        if (newUsername.isNotEmpty()) {
            if (newUsername != originalUsername && sharedPreferences.contains(newUsername)) {
                newUsernameEditText.error = "Username baru sudah terdaftar!"
                Toast.makeText(this, "Username baru sudah terdaftar!", Toast.LENGTH_SHORT).show()
                return
            }
            finalUsername = newUsername // Gunakan username baru jika valid
        }

        // Validasi untuk password baru
        var finalPassword = storedPassword!! // Default ke password lama jika tidak diubah
        if (newPassword.isNotEmpty()) {
            if (newPassword != confirmNewPassword) {
                confirmNewPasswordEditText.error = "Konfirmasi password baru tidak cocok!"
                Toast.makeText(this, "Konfirmasi password baru tidak cocok!", Toast.LENGTH_SHORT).show()
                return
            }
            if (newPassword.length < 6) { // Contoh validasi panjang password
                newPasswordEditText.error = "Password baru minimal 6 karakter!"
                Toast.makeText(this, "Password baru minimal 6 karakter!", Toast.LENGTH_SHORT).show()
                return
            }
            finalPassword = newPassword // Gunakan password baru jika valid
        } else if (newUsername.isEmpty()) {
            // Jika tidak ada username baru dan tidak ada password baru, berarti tidak ada yang diubah
            Toast.makeText(this, "Tidak ada perubahan yang dilakukan.", Toast.LENGTH_SHORT).show()
            return
        }


        val editor = sharedPreferences.edit()
        // Jika username diubah, hapus entri lama
        if (newUsername.isNotEmpty() && newUsername != originalUsername) {
            editor.remove(originalUsername)
        }
        // Simpan dengan username final (bisa jadi yang baru atau yang lama) dan password final
        editor.putString(finalUsername, finalPassword)
        editor.apply()

        Toast.makeText(this, "Kredensial berhasil diperbarui! Silakan login kembali.", Toast.LENGTH_LONG).show()

        // Kembali ke MainActivity dan hapus backstack untuk memaksa login ulang
        val intent = Intent(this, MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
        finish()
    }
}