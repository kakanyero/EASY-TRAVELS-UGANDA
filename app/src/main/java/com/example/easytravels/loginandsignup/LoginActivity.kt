package com.example.easytravels.loginandsignup

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import com.example.easytravels.MainActivity
import com.example.easytravels.R
import com.example.easytravels.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

       binding.logInButton.setOnClickListener {
            Intent(this,MainActivity::class.java).also {
                startActivity(it)
            }
        }


        binding.forgotPasswordTextField.setOnClickListener {
            Intent(this,ForgotPasswordActivity::class.java).also {
                startActivity(it)
            }
        }

        binding.signUpTextField.setOnClickListener {
            Intent(this,SignUpActivity::class.java).also {
                startActivity(it)
            }
        }






    }
}