package com.jop.testvascomm.data.repository

import com.jop.testvascomm.data.model.request.LoginRequest
import com.jop.testvascomm.network.api.AuthAPI

class AuthRepository(private val authAPI: AuthAPI) {

    suspend fun login(username: String, password: String) = authAPI.login(LoginRequest(username, password))

}