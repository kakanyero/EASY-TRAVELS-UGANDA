package com.example.easytravels.ui.activities

import android.app.Activity
import android.app.Dialog
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.easytravels.R
import com.example.easytravels.databinding.ProgressdialogBinding

open class BaseActivity : AppCompatActivity() {

    lateinit var progressDialog: Dialog

    /**
     * Creating A custom progress dialog which will be reused in any activity or fragment.
     */
    fun progressDialog(text: String) {
        val binding:ProgressdialogBinding = DataBindingUtil.setContentView(this, R.layout.progressdialog)
        progressDialog = Dialog(this)

        /*Set the screen content from a layout resource.
        The resource will be inflated, adding all top-level views to the screen.*/
        progressDialog.setContentView(R.layout.progressdialog)

        binding.tvProgdialg.text = text

        progressDialog.setCancelable(false)
        progressDialog.setCanceledOnTouchOutside(false)

        //Start the dialog and display it on screen.
        progressDialog.show()
    }

    // hiding/dismissing the progress dialog
    fun hideProgressDialog(){
        progressDialog.dismiss()
    }
}