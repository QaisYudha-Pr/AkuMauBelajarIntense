package com.hai.akumaubelajarintense

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast

class ProfileFragment : Fragment() {

    private lateinit var usernameValueTextView: TextView
    private lateinit var changeCredentialsButton: Button
    private lateinit var logoutButton: Button
    // private lateinit var sharedPreferences: SharedPreferences // SharedPreferences akan dihandle di ChangeCredentialsActivity

    private var currentUsername: String? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_profile, container, false)

        usernameValueTextView = view.findViewById(R.id.usernameValueTextView)
        changeCredentialsButton = view.findViewById(R.id.changeCredentialsButton)
        logoutButton = view.findViewById(R.id.logoutButton)

        // sharedPreferences = requireActivity().getSharedPreferences("UserPrefs", Context.MODE_PRIVATE)

        currentUsername = arguments?.getString("USERNAME_ARG")
        if (currentUsername != null && currentUsername!!.isNotEmpty()) {
            usernameValueTextView.text = currentUsername
        } else {
            usernameValueTextView.text = "Tidak diketahui"
        }

        changeCredentialsButton.setOnClickListener {
            if (currentUsername != null) {
                val intent = Intent(activity, ChangeCredentialsActivity::class.java).apply {
                    putExtra("EXTRA_USERNAME", currentUsername)
                }
                startActivity(intent)
            } else {
                Toast.makeText(context, "Username tidak diketahui, tidak dapat mengubah kredensial.", Toast.LENGTH_LONG).show()
            }
        }

        logoutButton.setOnClickListener {
            Toast.makeText(context, "Logout berhasil!", Toast.LENGTH_SHORT).show()
            val intent = Intent(activity, MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
            activity?.finish()
        }

        return view
    }
}