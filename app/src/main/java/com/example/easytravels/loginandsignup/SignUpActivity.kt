package com.example.easytravels.loginandsignup

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.easytravels.BaseActivity.BaseActivity
import com.example.easytravels.MainActivity
import com.example.easytravels.R
import com.example.easytravels.databinding.ActivitySignUpBinding
import com.example.easytravels.models.User
import com.example.easytravels.mvvm.FireBaseViewModelFactory
import com.example.easytravels.mvvm.FirebaseViewModel
import com.example.easytravels.mvvm.FirebaseViewModelRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.ktx.Firebase

class SignUpActivity : BaseActivity() {
    private lateinit var binding: ActivitySignUpBinding
    private lateinit var firebaseViewModel: FirebaseViewModel
    private lateinit var selectedGender:String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_sign_up)

        // Action bar setup
        setUpActionBar()

        // Initializing the ViewModel
        val repository = FirebaseViewModelRepository()
        val factory = FireBaseViewModelFactory(repository)
        firebaseViewModel = ViewModelProvider(this, factory)[FirebaseViewModel::class.java]

        // List of gender values
        val genderList = arrayOf("Male", "Female")

        // Referencing the auto complete text view
        val autoCompleteGender = binding.autoCompleteGender

        // Create the adapter
        val adapter = ArrayAdapter(this, R.layout.custom_gender, genderList)

        // setting the adapter
        autoCompleteGender.setAdapter(adapter)

        // Get selected value
        autoCompleteGender.setOnItemClickListener { adapterView, view, i, l ->
            selectedGender = adapterView.getItemAtPosition(i).toString()
        }


        // Create account button
        binding.buttonRegister.setOnClickListener {
            createUserAccount()
        }
        // Already have an account layout, then login
        binding.llAlreadyHaveAccount.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    private fun setUpActionBar(){
        val myToolBar = findViewById<Toolbar>(R.id.signup_toolbar)
        setSupportActionBar(myToolBar)
        val actionBar = supportActionBar
        if (actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true)
            actionBar.setHomeAsUpIndicator(R.drawable.ic_back_arrow)
        }

        myToolBar.setNavigationOnClickListener { onBackPressed() }
    }

    private fun validateUserDetails(
        name:String, address:String,
        phoneNumber:String, email:String, password:String, confirmPassword:String
    ) : Boolean{
        return when{
//            TextUtils.isEmpty(firstName.trim { it <= ' ' })->{
//                Toast.makeText(this, "First name is required", Toast.LENGTH_LONG).show()
//                false
//            }
//            TextUtils.isEmpty(lastName.trim { it <= ' ' })->{
//                Toast.makeText(this, "last name is required", Toast.LENGTH_LONG).show()
//                false
//            }
//            TextUtils.isEmpty(placeOfResidence.trim { it <= ' ' })->{
//                Toast.makeText(this, "place of residence is required", Toast.LENGTH_LONG).show()
//                false
//            }
//            TextUtils.isEmpty(phoneNumber.trim { it <= ' ' })->{
//                Toast.makeText(this, "Phone number is required", Toast.LENGTH_LONG).show()
//                false
//            }

            TextUtils.isEmpty(email.trim { it <= ' ' })->{
                Toast.makeText(this, "Email is required", Toast.LENGTH_LONG).show()
                false
            }
            TextUtils.isEmpty(password.trim { it <= ' ' })->{
                Toast.makeText(this, "Password is required", Toast.LENGTH_LONG).show()
                false
            }

//            TextUtils.isEmpty(confirmPassword.trim { it <= ' ' })->{
//                Toast.makeText(this, "First name is required", Toast.LENGTH_LONG).show()
//                false
//            }
//
//            password.length < 8 ->{
//                Toast.makeText(this, "Password must be eight characters and above",
//                    Toast.LENGTH_LONG).show()
//                false
//            }
//
//            password.trim { it <= ' ' } != confirmPassword.trim{it <= ' '} ->{
//                Toast.makeText(this, "Passwords do not match", Toast.LENGTH_LONG).show()
//                false
//            }
//
//            selectedGender.isEmpty() ->{
//                Toast.makeText(this, "Please select gender", Toast.LENGTH_LONG).show()
//                false
//            }
            else ->{
                true
            }
        }

    }


    private fun createUserAccount(){

        val email = binding.edRegEmail.text.toString()
        val password = binding.edRegPassword.text.toString()

        if (validateUserDetails(
                binding.edRegName.text.toString(), binding.edRegAddress.text.toString(),
                binding.edRegTelNo.text.toString(),
                binding.edRegEmail.text.toString(), binding.edRegPassword.text.toString(),
                binding.edRegConfirmPassword.text.toString()
            )
        ){
            firebaseViewModel.userSignup(this, email, password)
        }
    }

    fun signupSuccess(userId:String) {

        // After creating a user, then store other relevant info
        val name = binding.edRegName.text.toString()
        val address = binding.edRegAddress.text.toString()
        val phoneNumber = binding.edRegTelNo.text.toString()
        val email = binding.edRegEmail.text.toString()

        val userdata = User(name, address, phoneNumber, selectedGender, email, userId)
        firebaseViewModel.storeUserDataToCloud(this, userdata)
    }

    fun storeUserDataToFirestoreSuccess() {
        // Hide progress dialog
        hideProgressDialog()

        Toast.makeText(this, "Your account has been created successfully", Toast.LENGTH_LONG).show()
        val intent = Intent(this, MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
        finish()
    }
}