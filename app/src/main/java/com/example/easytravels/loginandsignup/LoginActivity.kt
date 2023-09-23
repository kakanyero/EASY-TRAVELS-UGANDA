package com.example.easytravels.loginandsignup

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.example.easytravels.MainActivity
import com.example.easytravels.R
import com.example.easytravels.databinding.ActivityLoginBinding
import com.google.firebase.auth.FirebaseAuth

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private lateinit var firebaseAuth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        firebaseAuth=FirebaseAuth.getInstance()

       binding.logInButton.setOnClickListener {
           val email = binding.logInEmailId.text.toString()
           val pass = binding.logInPasswordId.text.toString()

           if (email.isNotEmpty() && pass.isNotEmpty()) {

               firebaseAuth.signInWithEmailAndPassword(email, pass).addOnCompleteListener {
                   if (it.isSuccessful) {
                       val intent = Intent(this, MainActivity::class.java)
                       startActivity(intent)
                   } else {
                       Toast.makeText(this, it.exception.toString(), Toast.LENGTH_SHORT).show()

                   }
               }
           } else {
               Toast.makeText(this, "Empty Fields Are not Allowed !!", Toast.LENGTH_SHORT).show()

           }
        }


        binding.forgotPasswordTextField.setOnClickListener {
            Intent(this,ForgotPasswordActivity::class.java).also {
                startActivity(it)
            }
        }

        binding.signUpTextField.setOnClickListener {

            //val email = binding.signUpTextField.text.toString()
            Intent(this,SignUpActivity::class.java).also {
                startActivity(it)
            }
        }


    }
    override fun onStart() {
        super.onStart()

        if(firebaseAuth.currentUser != null){
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }
}