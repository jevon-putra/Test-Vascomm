package com.jop.testvascomm.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar
import com.jop.testvascomm.R
import com.jop.testvascomm.data.local.AppData
import com.jop.testvascomm.ui.component.CustomSnackbar
import com.jop.testvascomm.ui.component.LoadingDialog
import com.jop.testvascomm.view.MainActivity

open class BaseFragment: Fragment() {
    private lateinit var loadingDialog: LoadingDialog
    lateinit var appData: AppData
    lateinit var mainAct: MainActivity

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mainAct = activity as MainActivity

        loadingDialog = LoadingDialog(requireContext())
        appData = AppData(requireContext())
    }

    fun showLoadingDialog(){
        loadingDialog.showDialog()
    }

    fun hideLoadingDialog(){
        loadingDialog.hideDialog()
    }

    fun showFailedToast(text: String) {
        CustomSnackbar.make(
            requireActivity().findViewById(android.R.id.content), text, Snackbar.LENGTH_SHORT,
            R.color.red,
            R.drawable.ic_failed_snackbar
        )?.show()
    }

    fun showSuccessToast(text: String) {
        CustomSnackbar.make(
            requireActivity().findViewById(android.R.id.content), text, Snackbar.LENGTH_SHORT,
            R.color.green_success,
            R.drawable.ic_success_snackbar,
        )?.show()
    }
}