package com.hai.akumaubelajarintense

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView // Import TextView

class HomeFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_home, container, false)

        val welcomeTitleTextView: TextView = view.findViewById(R.id.welcomeTitleTextView)
        val username = arguments?.getString("USERNAME_ARG")

        if (username != null && username.isNotEmpty()) {
            welcomeTitleTextView.text = "Selamat Datang Kembali, $username!"
        } else {
            welcomeTitleTextView.text = "Selamat Datang Kembali!" // Fallback
        }

        return view
    }
}