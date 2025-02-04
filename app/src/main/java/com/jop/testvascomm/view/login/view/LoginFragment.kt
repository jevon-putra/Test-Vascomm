package com.jop.testvascomm.view.login.view

import android.os.Bundle
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import com.jop.testvascomm.R
import com.jop.testvascomm.databinding.FragmentLoginBinding
import com.jop.testvascomm.ui.BaseFragment
import com.jop.testvascomm.view.login.viewModel.LoginViewModel
import com.sam.sewasam.data.Resource
import org.koin.androidx.viewmodel.ext.android.viewModel

class LoginFragment : BaseFragment() {
    private lateinit var binding: FragmentLoginBinding
    private val loginVM by viewModel<LoginViewModel>()
    private var hidePassword: Boolean = true

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            etEmail.setOnEditorActionListener { _, actionId, _ ->
                if (actionId == EditorInfo.IME_ACTION_NEXT) {
                    etPassword.requestFocus()
                    return@setOnEditorActionListener true
                }
                false
            }

            etPassword.setOnEditorActionListener { _, actionId, _ ->
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    loginVM.doLogin(etEmail.text.toString(), etPassword.text.toString())
                    return@setOnEditorActionListener true
                }
                false
            }

            ivShowHide.setOnClickListener {
                if(hidePassword){
                    etPassword.transformationMethod = PasswordTransformationMethod.getInstance()
                } else {
                    etPassword.transformationMethod = HideReturnsTransformationMethod.getInstance()
                }

                hidePassword = !hidePassword
                etPassword.setSelection(etPassword.text.toString().length)
            }

            btnLogin.setOnClickListener{
                if(etEmail.toString().length < 5) {
                    showFailedToast("Email tidak valid")
                } else if(etPassword.toString().length < 5){
                    showFailedToast("Password minimal 6 karakter")
                } else {
                    loginVM.doLogin(etEmail.text.toString(), etPassword.text.toString())
                }
            }

            tvRegisterHere.setOnClickListener {
                mainAct.navController.navigate(R.id.action_loginFragment_to_registerFragment)
            }
        }

        setupObservable()
    }

    private fun setupObservable() {
        loginVM.loginAction.observe(viewLifecycleOwner){
            when(it){
                is Resource.Loading -> {
                    showLoadingDialog()
                }
                is Resource.Success -> {
                    hideLoadingDialog()
                    mainAct.navController.navigate(R.id.action_loginFragment_to_homeFragment)
                }
                is Resource.Error -> {
                    hideLoadingDialog()
                    showFailedToast(it.message.toString())
                }
            }
        }
    }
}