package com.example.easytravels.mvvm

import android.widget.Toast
import com.example.easytravels.loginandsignup.ForgotPasswordActivity
import com.example.easytravels.loginandsignup.LoginActivity
import com.example.easytravels.loginandsignup.SignUpActivity
import com.example.easytravels.models.Constants
import com.example.easytravels.models.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class FirebaseViewModelRepository {
    private val mFirebaseAuth = FirebaseAuth.getInstance()
    private var mFirestore = FirebaseFirestore.getInstance()

    // Authentication | Login
    fun userLogin(activity: LoginActivity, userLoginEmail:String, userLoginPassword:String){
        activity.progressDialog()
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
        activity.progressDialog()
        mFirebaseAuth.createUserWithEmailAndPassword(
            userSignupEmail,
            userSignupPassword
        ).addOnCompleteListener {
            if (it.isSuccessful) {
                val userId = mFirebaseAuth.currentUser!!.uid
                activity.signupSuccess(userId)
            }
        }
            .addOnFailureListener {
                // Hide progress dialog
                activity.hideProgressDialog()

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

    // Storing more user details to cloud
    fun storeUserDataToCloud(activity: SignUpActivity, user: User){
        mFirestore.collection(Constants.USERS_COLLECTION)
            .document()
            .set(user)
            .addOnSuccessListener {
                activity.storeUserDataToFirestoreSuccess()
            }
    }
}