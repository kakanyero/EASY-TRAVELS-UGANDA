package com.example.easytravels.loginandsignup

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.easytravels.BaseActivity.BaseActivity
import com.example.easytravels.MainActivity
import com.example.easytravels.R
import com.example.easytravels.databinding.ActivityLoginBinding
import com.example.easytravels.mvvm.FireBaseViewModelFactory
import com.example.easytravels.mvvm.FirebaseViewModel
import com.example.easytravels.mvvm.FirebaseViewModelRepository
import com.google.firebase.auth.FirebaseAuth

class LoginActivity : BaseActivity() {
    private lateinit var binding: ActivityLoginBinding
    private lateinit var firebaseViewModel: FirebaseViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login)

        // Initializing the ViewModel
        val repository = FirebaseViewModelRepository()
        val factory = FireBaseViewModelFactory(repository)
        firebaseViewModel = ViewModelProvider(this, factory)[FirebaseViewModel::class.java]


        binding.logInButton.setOnClickListener {
            val userEmail = binding.loginUserEmail.text.toString()
            val userPassword = binding.loginUserPassword.text.toString()

            if (validateUserLoginData(userEmail, userPassword)) {
                firebaseViewModel.userLogin(this, userEmail, userPassword)
            }
        }


        binding.forgotPasswordTextField.setOnClickListener {
            val intent = Intent(this, ForgotPasswordActivity::class.java)
            startActivity(intent)

        }

        binding.signUpTextField.setOnClickListener {
            val intent = Intent(this, SignUpActivity::class.java)
            startActivity(intent)
            finish()
        }

    }

    private fun validateUserLoginData(userEmail: String, userPassword: String): Boolean {

        return when {
            TextUtils.isEmpty(userEmail.trim { it <= ' ' }) -> {
                Toast.makeText(this, "Email Id cannot be empty", Toast.LENGTH_LONG).show()
                false
            }

            TextUtils.isEmpty(userPassword.trim { it <= ' ' }) -> {
                Toast.makeText(this, "Password field cannot be empty", Toast.LENGTH_LONG).show()
                false
            }
            else -> {
                true
            }
        }
    }

    fun loginSuccess() {
        hideProgressDialog()

        val intent = Intent(this, MainActivity::class.java)

        // Getting rid of the other layers running in the background say if
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
        finish()
    }
}