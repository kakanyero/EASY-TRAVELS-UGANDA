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

    }

    fun signupSuccess(serSignupEmail:String, userSignupPassword:String) {
        TODO("Not yet implemented")
    }
}