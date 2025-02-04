package com.jop.testvascomm.network.client

import com.jop.testvascomm.BuildConfig
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object ClientNetwork {
    fun createRetrofit(): Retrofit{
        val okHttpClient = createClientRetrofit()
        return Retrofit.Builder()
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(ConstantAPI.BASE_URL)
            .build()
    }

    private fun createClientRetrofit(): OkHttpClient{
        val loggingInterceptor = HttpLoggingInterceptor()
        val builder = OkHttpClient.Builder()
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY

        builder.connectTimeout(30, TimeUnit.SECONDS)
        builder.readTimeout(30, TimeUnit.SECONDS)
        builder.writeTimeout(30, TimeUnit.SECONDS)

        builder.addInterceptor(ResponseErrorInterceptor())

        builder.addInterceptor(Interceptor { chain ->
            val original = chain.request()
            val request = original.newBuilder()
                .header("Accept", "application/json")
                .build()
            chain.proceed(request)
        })

        if (BuildConfig.DEBUG) builder.addInterceptor(loggingInterceptor)
        return builder.build()
    }
}