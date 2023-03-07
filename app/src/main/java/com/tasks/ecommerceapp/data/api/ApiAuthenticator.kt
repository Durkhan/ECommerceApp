package com.tasks.ecommerceapp.data.api

import okhttp3.OkHttpClient
import javax.inject.Inject

class ApiAuthenticator @Inject constructor(private var okHttpClient: OkHttpClient) {
    fun setAuthToken(token: String?) {
        val interceptors = okHttpClient.interceptors
        val modifiedInterceptors = interceptors.toMutableList()
        if (token != null) {
            modifiedInterceptors.add { chain ->
                val request = chain.request().newBuilder()
                    .addHeader("Authorization",token)
                    .build()
                chain.proceed(request)
            }

        } else {
            modifiedInterceptors.clear()
        }
    }

}