package com.example.easytravels.mvvm

import android.app.Activity
import android.content.Context
import android.util.Log
import android.widget.Toast
import com.example.easytravels.BaseActivity.BaseActivity
import com.example.easytravels.loginandsignup.ForgotPasswordActivity
import com.example.easytravels.loginandsignup.LoginActivity
import com.example.easytravels.loginandsignup.SignUpActivity
import com.example.easytravels.models.*
import com.example.easytravels.ui.activities.AddBus
import com.example.easytravels.ui.activities.Booking
import com.example.easytravels.ui.activities.BusDetails
import com.example.easytravels.ui.buses.BusesFragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.toObject

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
                activity.hideProgressDialog()
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

    // Storing bus details to cloud
    fun storeBusDetailsToCloud(activity: AddBus, busToAdd: Bus){
        activity.progressDialog()
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

    // Get the bus list from the cloud
    fun downloadBusDetailsFromCloud(fragment: BusesFragment){
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

    // Get bus details from cloud
    fun getExtraBusDetailsFromCloud(activity:BusDetails, bus_id:String){
        activity.progressDialog()
        mFirestore.collection(Constants.BUS_COLLECTION)
            .document(bus_id)
            .get()
            .addOnSuccessListener {bus->
                val busDetails = bus.toObject(Bus::class.java)
                if (busDetails != null) {
                    busDetails.id = bus.id
                    activity.getExtraBusDetailsFromCloudSuccess(busDetails)
                }
            }
    }

    fun makeBooking(activity:BusDetails, book:BookingClass){
        activity.progressDialog()
        mFirestore.collection(Constants.BOOKING_COLLECTION)
            .document()
            .set(book)
            .addOnSuccessListener {
//                activity.hideProgressDialog()

                activity.makeBookingSuccess()
            }
            .addOnFailureListener {
                activity.hideProgressDialog()
                Toast.makeText(activity, "Error while booking", Toast.LENGTH_SHORT).show()
            }
    }


    // Downloading bookings
    fun getBookingsFromCloud(activity: Booking){
        activity.progressDialog()
        mFirestore.collection(Constants.BOOKING_COLLECTION)
                // The whereEqualTo Clause will filter to load bookings for only user logged in
            .whereEqualTo(Constants.USER_ID, Constants.getCurrentUserID())
            .get()
            .addOnSuccessListener { bookings ->
                val bookingsList:ArrayList<BookingClass> = ArrayList()

                // Loop through the results, changing each item/Booking into an object
                // of type BookingClass
                for (i in bookings.documents){
                    val singleBooking = i.toObject(BookingClass::class.java)

                    // Assign the document id to the booking empty id
                    singleBooking!!.booking_id = i.id

                    bookingsList.add(singleBooking)
                }
                activity.downloadBookingsFromFireStoreSuccess(bookingsList)
            }
            .addOnFailureListener {
                activity.hideProgressDialog()
                Toast.makeText(activity, "Error while downloading the booking list", Toast.LENGTH_SHORT).show()
            }

    }

    fun deleteBookingFromCloud(activity: Booking, bookingId:String){
        activity.progressDialog()
        mFirestore.collection(Constants.BOOKING_COLLECTION)
            .document(bookingId)
            .delete()
            .addOnSuccessListener {
                activity.deleteBookingSuccess()
            }
            .addOnFailureListener {
                activity.hideProgressDialog()
                Toast.makeText(activity, "Error while deleting the booking", Toast.LENGTH_SHORT).show()
            }
    }

    fun updateBooking(context: Booking, booking_id:String, itemHashMap:HashMap<String, Any>){
//        context.progressDialog()
        mFirestore.collection(Constants.BOOKING_COLLECTION)
            .document(booking_id)
            /**
             * HashMap will help to pass in the whole object but will help to edit a
             * specific attribute of this object passed in this case the stock_quantity
             */
            .update(itemHashMap)
            .addOnSuccessListener {
                context.updateBookingSuccess()
            }
            .addOnFailureListener {
                context.hideProgressDialog()
                Toast.makeText(context, "Error while updating the booking", Toast.LENGTH_SHORT).show()
            }
    }

//    fun getBusRoute(activity: BusDetails, route:String){
//        activity.progressDialog()
//        mFirestore.collection(Constants.BUS_ROUTES_COLLECTION)
//            .whereEqualTo(Constants.BUS_ROUTE, route)
//            .get()
//            .addOnSuccessListener { routes->
//
//                for (i in routes.documents) {
//                    val routeObject = i.toObject(Route::class.java)!!
//                    activity.getBusRouteSuccess(routeObject)
//                }
//            }
//            .addOnFailureListener {
//                Toast.makeText(activity, "Error while getting bus routes", Toast.LENGTH_LONG).show()
//            }
//    }


}