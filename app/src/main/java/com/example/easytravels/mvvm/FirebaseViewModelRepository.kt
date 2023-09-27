package com.example.easytravels.mvvm

import android.app.Activity
import android.content.Intent
import android.util.Log
import android.widget.Toast
import com.example.easytravels.loginandsignup.ForgotPasswordActivity
import com.example.easytravels.loginandsignup.LoginActivity
import com.example.easytravels.loginandsignup.SignUpActivity
import com.google.firebase.auth.FirebaseAuth

class FirebaseViewModelRepository {

    private val mFirebaseAuth: FirebaseAuth = FirebaseAuth.getInstance()

    // Authentication | Login
    fun userLogin(activity:LoginActivity, userLoginEmail:String, userLoginPassword:String){
        mFirebaseAuth.signInWithEmailAndPassword(userLoginEmail, userLoginPassword)
            .addOnSuccessListener {
                activity.loginSuccess()
            }
            .addOnFailureListener {
                Toast.makeText(
                    activity, " Error on login",
                    Toast.LENGTH_SHORT
                ).show()
            }
    }

    // Authentication | Signup
    fun userSignUp(activity: SignUpActivity, userSignupEmail:String, userSignupPassword:String){
        mFirebaseAuth.signInWithEmailAndPassword(
            userSignupEmail,
            userSignupPassword
        ).addOnSuccessListener {
                activity.signupSuccess(userSignupEmail, userSignupPassword)
            }
            .addOnFailureListener {
                Toast.makeText(
                    activity, " Error while signing up the user",
                    Toast.LENGTH_SHORT
                ).show()
            }
    }

    // Authentication | Forgot password
    fun sendResetPasswordLink(activity: ForgotPasswordActivity, emailWhereToSendTheLink:String) {
        mFirebaseAuth.sendPasswordResetEmail(emailWhereToSendTheLink)
            .addOnSuccessListener {
                activity.sendForgotPasswordLinkSuccess()
            }.addOnFailureListener {
                Toast.makeText(
                    activity,
                    "Error while sending the link, please try again",
                    Toast.LENGTH_SHORT
                ).show()
            }
    }
}