package com.example.easytravels.loginandsignup

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.example.easytravels.R
import com.example.easytravels.databinding.ActivityForgotPasswordBinding
import com.google.firebase.auth.FirebaseAuth

class ForgotPasswordActivity : AppCompatActivity() {
    private lateinit var binding: ActivityForgotPasswordBinding
    private val mFirebaseAuth:FirebaseAuth = FirebaseAuth.getInstance()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // DataBinding
        binding = DataBindingUtil.setContentView(this, R.layout.activity_forgot_password)

        binding.buttonSendEmail.setOnClickListener {
            val emailWhereToSendTheLink = binding.edForgotPasswordEmail.text.toString()
            sendResetPasswordLink(emailWhereToSendTheLink)
        }
    }

    private fun sendResetPasswordLink(email:String) {
        mFirebaseAuth.sendPasswordResetEmail(email)
            .addOnSuccessListener {
                Toast.makeText(
                    this,
                    "Link has been sent, please check your email to reset your password",
                    Toast.LENGTH_SHORT
                ).show()


                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
                finish()

            }.addOnFailureListener {
                Toast.makeText(
                    this,
                    "Error while sending the link, please try again",
                    Toast.LENGTH_SHORT
                ).show()
            }
    }
}