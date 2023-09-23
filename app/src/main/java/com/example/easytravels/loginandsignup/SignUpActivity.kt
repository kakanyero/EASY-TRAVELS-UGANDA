package com.example.easytravels.loginandsignup

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import com.example.easytravels.R
import com.example.easytravels.databinding.ActivitySignUpBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.ktx.Firebase

class SignUpActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySignUpBinding
    private lateinit var firebaseAuth: FirebaseAuth
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

        //Initialising firebase authentication inside the oncreate
        firebaseAuth = FirebaseAuth.getInstance()

        binding.loginforalreadyregisterd.setOnClickListener {
            val intent = Intent(this,LoginActivity::class.java)
            startActivity(intent)
        }

        binding.registerButton.setOnClickListener{
            val email = binding.signUpEmailId.text.toString()
            val password = binding.signUPasswordId.text.toString()
            val confirmPassword= binding.signUpConfirmPasswordId.text.toString()

            if(email.isNotEmpty() && password.isNotEmpty() && confirmPassword.isNotEmpty()){
                if(password==confirmPassword){
                    firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener {
                        if(it.isSuccessful){
                            val intent = Intent(this,LoginActivity::class.java)
                            startActivity(intent)
                        }else{
                            Toast.makeText(this,it.exception.toString(),Toast.LENGTH_SHORT).show()

                        }
                    }
                }else{
                    Toast.makeText(this,"Passwords not matching",Toast.LENGTH_SHORT).show()
                }
            }else{
                Toast.makeText(this,"Fields cannot be empty",Toast.LENGTH_SHORT).show()
            }
    }
}
}