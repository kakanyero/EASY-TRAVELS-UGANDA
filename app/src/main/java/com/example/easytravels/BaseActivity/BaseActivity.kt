package com.example.easytravels.BaseActivity

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.easytravels.R
import com.example.easytravels.databinding.CustomProgressDialogBinding
import com.example.easytravels.loginandsignup.LoginActivity

open class BaseActivity : AppCompatActivity() {

    lateinit var progressDialog: Dialog

    /**
     * Creating A custom progress dialog which will be reused in any activity or fragment.
     */
    fun progressDialog() {

        progressDialog = Dialog(this)

        /*Set the screen content from a layout resource.
        The resource will be inflated, adding all top-level views to the screen.*/
        progressDialog.setContentView(R.layout.custom_progress_dialog)

//        val text1 = findViewById<TextView>(R.id.tv_progdialg)
//        text1.text = text

        progressDialog.setCancelable(false)
        progressDialog.setCanceledOnTouchOutside(false)

        //Start the dialog and display it on screen.
        progressDialog.show()
    }

    // hiding/dismissing the progress dialog
    fun hideProgressDialog() {
        progressDialog.dismiss()
    }

    // Creating a general alert dialog
    fun showAlertDialogForLogout(context: Context, title:String, message_to_display:String){
        val alertDialogBuilder = AlertDialog.Builder(context)

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