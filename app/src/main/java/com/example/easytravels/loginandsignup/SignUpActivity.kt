package com.example.easytravels.loginandsignup

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import com.example.easytravels.R
import com.example.easytravels.databinding.ActivitySignUpBinding

class SignUpActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySignUpBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val gender_list = listOf("Male", "Female", "None")
        val adapter = ArrayAdapter(this,R.layout.gender, gender_list)
        binding.genderId.setAdapter(adapter)
        /**
        val adapter = ArrayAdapter(requireContext(),R.layout.gender, gender_list)
        The requireContext() function is typically used within a Fragment to get the
        context of the parent Activity.
        In your case, you are working within an AppCompatActivity, so you
        should use this instead of requireContext() to get the context.
         */
    }
}