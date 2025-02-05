package com.jop.testvascomm.view.register.view

import android.os.Bundle
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import com.jop.testvascomm.R
import com.jop.testvascomm.databinding.FragmentLoginBinding
import com.jop.testvascomm.databinding.FragmentRegisterBinding
import com.jop.testvascomm.ui.BaseFragment
import com.jop.testvascomm.view.MainActivity
import com.jop.testvascomm.view.login.viewModel.LoginViewModel
import com.sam.sewasam.data.Resource
import org.koin.androidx.viewmodel.ext.android.viewModel

class RegisterFragment : BaseFragment() {
    private lateinit var binding: FragmentRegisterBinding
    private var hidePassword: Boolean = true
    private var hidePasswordConfirmation: Boolean = true

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentRegisterBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            ivShowHide.setOnClickListener {
                if(hidePassword){
                    etPassword.transformationMethod = PasswordTransformationMethod.getInstance()
                } else {
                    etPassword.transformationMethod = HideReturnsTransformationMethod.getInstance()
                }

                hidePassword = !hidePassword
                etPassword.setSelection(etPassword.text.toString().length)
            }

            ivShowHideCondirmation.setOnClickListener {
                if(hidePasswordConfirmation){
                    etPasswordConfirmation.transformationMethod = PasswordTransformationMethod.getInstance()
                } else {
                    etPasswordConfirmation.transformationMethod = HideReturnsTransformationMethod.getInstance()
                }

                hidePasswordConfirmation = !hidePasswordConfirmation
                etPasswordConfirmation.setSelection(etPasswordConfirmation.text.toString().length)
            }

            tvLoginHere.setOnClickListener {
                mainAct.navController.popBackStack()
            }
        }
    }
}