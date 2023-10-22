package com.example.easytravels

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.content.Intent
import androidx.fragment.app.Fragment
import com.example.easytravels.loginandsignup.LoginActivity

class BaseFragment : Fragment() {
        lateinit var mProgressDialog: Dialog

        /**
         * This function is used to show the progress dialog with the title and message to user.
         */
        fun progressDialog(text: String) {
            mProgressDialog = Dialog(requireActivity())

            /*Set the screen content from a layout resource.
            The resource will be inflated, adding all top-level views to the screen.*/
            mProgressDialog.setContentView(R.layout.custom_progress_dialog)
            mProgressDialog.setTitle(text)
            mProgressDialog.setCancelable(false)
            mProgressDialog.setCanceledOnTouchOutside(false)

            //Start the dialog and display it on screen.
            mProgressDialog.show()
        }

        fun hideProgressDialog(){
            mProgressDialog.dismiss()
        }


        // Creating a general alert dialog
        fun showAlertDialogForLogout(context: Context, title:String, message_to_display:String){
            val alertDialogBuilder = AlertDialog.Builder(activity)

            // Setting the Title, message and icon for the alert
            alertDialogBuilder.setTitle(title)
            alertDialogBuilder.setMessage(message_to_display)
            alertDialogBuilder.setIcon(R.drawable.ic_warning)

            // Setting the positive action when a yes is selected
            alertDialogBuilder.setPositiveButton(resources.getString(R.string.yes)){
                    dialogInterface,_->
                // Logout
                val intent = Intent(context, LoginActivity::class.java)
                // Removing the flags to avoid getting back to the background activities running
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(intent)
            }

            // Setting the negative action when a yes is selected
            alertDialogBuilder.setNegativeButton(resources.getString(R.string.No)){
                    dialogInterface,_->
                dialogInterface.dismiss()
            }

            // Using the alert dialog structure above to show the alert
            val alertDialog: AlertDialog = alertDialogBuilder.create()
            alertDialog.setCancelable(false)
            alertDialog.show()
        }
}