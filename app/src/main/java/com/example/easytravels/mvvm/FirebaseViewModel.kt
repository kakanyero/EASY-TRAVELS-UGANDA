package com.example.easytravels.mvvm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.easytravels.loginandsignup.ForgotPasswordActivity
import com.example.easytravels.loginandsignup.LoginActivity
import com.example.easytravels.loginandsignup.SignUpActivity
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.launch

class FirebaseViewModel(private val repository: FirebaseViewModelRepository):ViewModel() {
    private val mFirebaseAuth: FirebaseAuth = FirebaseAuth.getInstance()
    // Login
    fun userLogin(activity: LoginActivity, userLoginEmail:String, userLoginPassword:String){
        viewModelScope.launch {
            repository.userLogin(activity, userLoginEmail, userLoginPassword)
        }
    }

    fun userSignup(activity: SignUpActivity, userSignupEmail:String, userSignupPassword:String){
        viewModelScope.launch {
            repository.userSignUp(activity, userSignupEmail, userSignupPassword)
        }
    }

    fun sendResetPasswordLink(activity: ForgotPasswordActivity, emailWhereToSendTheLink:String) {
        viewModelScope.launch {
            repository.sendResetPasswordLink(activity, emailWhereToSendTheLink)
        }
    }
}