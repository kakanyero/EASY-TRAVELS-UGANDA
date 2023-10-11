package com.example.easytravels.BaseActivity

import android.app.Dialog
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.easytravels.R
import com.example.easytravels.databinding.CustomProgressDialogBinding

open class BaseActivity : AppCompatActivity() {

    lateinit var progressDialog: Dialog

    /**
     * Creating A custom progress dialog which will be reused in any activity or fragment.
     */
    fun progressDialog() {
        val binding: CustomProgressDialogBinding =
            DataBindingUtil.setContentView(this, R.layout.custom_progress_dialog)
        progressDialog = Dialog(this)

        /*Set the screen content from a layout resource.
        The resource will be inflated, adding all top-level views to the screen.*/
        progressDialog.setContentView(R.layout.custom_progress_dialog)

//        binding.tvProgdialg.text = text

        progressDialog.setCancelable(false)
        progressDialog.setCanceledOnTouchOutside(false)

        //Start the dialog and display it on screen.
        progressDialog.show()
    }

    // hiding/dismissing the progress dialog
    fun hideProgressDialog() {
        progressDialog.dismiss()
    }
}