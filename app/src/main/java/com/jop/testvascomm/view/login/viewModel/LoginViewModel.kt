package com.jop.testvascomm.view.login.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jop.testvascomm.data.local.AppData
import com.jop.testvascomm.data.repository.AuthRepository
import com.sam.sewasam.data.Resource
import kotlinx.coroutines.launch

class LoginViewModel(private val repository: AuthRepository, private val appData: AppData): ViewModel() {
    val loginAction : MutableLiveData<Resource<String>> = MutableLiveData()

    fun doLogin(username: String, password: String) = viewModelScope.launch {
        loginAction.value = Resource.Loading()

        try {
            val response = repository.login(username, password)

            if(response.isSuccessful){
                loginAction.value = Resource.Success("")
                appData.setToken(response.body()?.token.toString())
            } else {
                loginAction.value = Resource.Error(response.message())
            }
        } catch (e: Exception){
            loginAction.value = Resource.Error(e.message.toString())
        }
    }

}