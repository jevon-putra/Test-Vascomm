package com.jop.testvascomm.di

import com.jop.testvascomm.network.api.AuthAPI
import com.jop.testvascomm.network.client.ClientNetwork
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.create

val networkModule = module {
    fun clientNetwork() = ClientNetwork.createRetrofit()

    single<AuthAPI> { clientNetwork().create(AuthAPI::class.java) }
}