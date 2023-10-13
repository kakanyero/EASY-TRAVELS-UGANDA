package com.example.easytravels.mvvm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.easytravels.loginandsignup.ForgotPasswordActivity
import com.example.easytravels.loginandsignup.LoginActivity
import com.example.easytravels.loginandsignup.SignUpActivity
import com.example.easytravels.models.Bus
import com.example.easytravels.models.User
import com.example.easytravels.ui.activities.AddBus
import com.example.easytravels.ui.buses.BusesFragment
import kotlinx.coroutines.launch

class FirebaseViewModel(private val repository: FirebaseViewModelRepository): ViewModel() {

    // Login
    fun userLogin(activity: LoginActivity, userLoginEmail:String, userLoginPassword:String){
        viewModelScope.launch {
            repository.userLogin(activity, userLoginEmail, userLoginPassword)
        }
    }

    // Register an account
    fun userSignup(activity: SignUpActivity, userSignupEmail:String, userSignupPassword:String){
        viewModelScope.launch {

            repository.userSignUp(activity, userSignupEmail, userSignupPassword)
        }
    }

    // Forgot Password
    fun sendResetPasswordLink(activity: ForgotPasswordActivity, emailWhereToSendTheLink:String) {
        viewModelScope.launch {
            repository.sendResetPasswordLink(activity, emailWhereToSendTheLink)
        }
    }

    // Store more user details
    fun storeUserDataToCloud(activity: SignUpActivity, user: User){
        viewModelScope.launch {
            repository.storeUserDataToCloud(activity, user)
        }
    }

    fun storeBusDetailsToCloud(activity: AddBus, busToAdd: Bus){
        viewModelScope.launch {
            repository.storeBusDetailsToCloud(activity, busToAdd)
        }
    }

    fun downloadBusDetailsFromCloud(fragment: BusesFragment){
        viewModelScope.launch {
            repository.downloadBusDetailsFromCloud(fragment)
        }
    }


}