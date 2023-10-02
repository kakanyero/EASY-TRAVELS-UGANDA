package com.example.easytravels.mvvm

import android.util.Log
import android.widget.Toast
import com.example.easytravels.loginandsignup.ForgotPasswordActivity
import com.example.easytravels.loginandsignup.LoginActivity
import com.example.easytravels.loginandsignup.SignUpActivity
import com.example.easytravels.models.Bus
import com.example.easytravels.models.Constants
import com.example.easytravels.ui.activities.AddBus
import com.example.easytravels.ui.busses.BussesFragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class FirebaseViewModelRepository {
    private val mFirebaseAuth: FirebaseAuth = FirebaseAuth.getInstance()
    private var mFirestore = FirebaseFirestore.getInstance()

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

    fun storeBusDetailsToCloud(activity: AddBus, busToAdd: Bus){
        mFirestore.collection(Constants.BUS_COLLECTION)
            .document()
            .set(busToAdd)
            .addOnSuccessListener {
                activity.addBusDetailsToCloudSuccess()
            }
            .addOnFailureListener {
                Toast.makeText(activity, "Error while uploading bus details", Toast.LENGTH_SHORT).show()
            }
    }

    fun downloadBusDetailsFromCloud(fragment: BussesFragment){
        mFirestore.collection(Constants.BUS_COLLECTION)
            .get()
            .addOnSuccessListener { buses->

                // Array List that will accommodate all the buses after being converted to objects
                val busList:ArrayList<Bus> = ArrayList()

                for (i in buses.documents){
                    // change each bus details to an object of Bus type
                    val singleBusObject = i.toObject(Bus::class.java)

                    // Assigning the document id of each bus to the id variable
                    singleBusObject!!.id = i.id

                    // Add the object into the ArrayList
                    busList.add(singleBusObject)
                }

                // After adding all the buses to the list, then we can pass the list
                fragment.downloadBusListFromFireStoreSuccess(busList)
            }
    }

}