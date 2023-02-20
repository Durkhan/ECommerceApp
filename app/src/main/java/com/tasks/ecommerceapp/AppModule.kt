package com.tasks.ecommerceapp

import com.tasks.ecommerceapp.api.ApiAuthenticator
import com.tasks.ecommerceapp.api.CustomerRepository
import com.tasks.ecommerceapp.api.CustomerService
import com.tasks.ecommerceapp.utils.Constants.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideOkHttpClient(): OkHttpClient {
        val loggingInterceptor = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
        return OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .build()
    }



    @Singleton
    @Provides
    fun provideCustomerService(mOkHttpClient: OkHttpClient): CustomerService {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create())
            .client(mOkHttpClient)
            .build()

        return retrofit.create(CustomerService::class.java)
    }



    @Singleton
    @Provides
    fun provideRepository(service: CustomerService): CustomerRepository {
        return CustomerRepository(service)
    }

    @Singleton
    @Provides
    fun provideApiAuthenticator(mOkHttpClient: OkHttpClient):ApiAuthenticator{
        return ApiAuthenticator(mOkHttpClient)
    }


}
