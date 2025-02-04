package com.jop.testvascomm.ui.component

import android.app.Dialog
import android.content.Context
import android.view.Window
import android.widget.TextView
import com.jop.testvascomm.R

class LoadingDialog(private var context: Context) {
    private lateinit var dialog: Dialog

    fun showDialog() {
        dialog = Dialog(context)

        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.custom_loading_layout)

        dialog.show()
    }

    fun hideDialog() {
        if(::dialog.isInitialized) dialog.dismiss()
    }

    fun updateMessage(message: String){
        val tvMessage = dialog.findViewById<TextView>(R.id.tv_message)
        tvMessage.text = message.ifEmpty { "Tunggu Ya..." }
    }

}