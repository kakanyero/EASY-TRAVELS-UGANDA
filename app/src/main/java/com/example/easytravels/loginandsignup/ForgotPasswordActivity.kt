package com.example.easytravels.loginandsignup

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.easytravels.R
import com.example.easytravels.databinding.ActivityForgotPasswordBinding
import com.example.easytravels.mvvm.FireBaseViewModelFactory
import com.example.easytravels.mvvm.FirebaseViewModel
import com.example.easytravels.mvvm.FirebaseViewModelRepository

class ForgotPasswordActivity : AppCompatActivity() {
    private lateinit var firebaseViewModel: FirebaseViewModel
    private lateinit var binding: ActivityForgotPasswordBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Initializing the ViewModel
        val repository = FirebaseViewModelRepository()
        val factory = FireBaseViewModelFactory(repository)
        firebaseViewModel = ViewModelProvider(this, factory)[FirebaseViewModel::class.java]

        // DataBinding
        binding = DataBindingUtil.setContentView(this, R.layout.activity_forgot_password)

        // Sending the link to the email
        binding.buttonSendEmail.setOnClickListener {
            firebaseViewModel.sendResetPasswordLink(
                this@ForgotPasswordActivity,
                binding.edForgotPasswordEmail.text.toString())
        }
    }

    fun sendForgotPasswordLinkSuccess(){
        Toast.makeText(this, resources.getString(R.string.forgot_link), Toast.LENGTH_LONG).show()
        val intent = Intent(this, LoginActivity::class.java)

        // Getting rid of the other layers running in the background say if
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
        finish()
    }
}