package com.example.assignmentswigg.di

import com.example.assignmentswigg.api.MovieApi
import com.example.assignmentswigg.utils.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton


@InstallIn(SingletonComponent::class)
@Module
class NetworkModule {


    private var client = OkHttpClient().newBuilder()
        .connectTimeout(60, TimeUnit.SECONDS)
        .readTimeout(60, TimeUnit.SECONDS)
        .writeTimeout(60, TimeUnit.SECONDS)
        .build()


    @Singleton
    @Provides
    fun providesRetrofit(): Retrofit.Builder{
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(Constants.BASE_URL)
            .client(client)

    }

    @Singleton
    @Provides
    fun providesUserApi(retrofitBuilder: Retrofit.Builder): MovieApi {
        return retrofitBuilder.client(client).build().create(MovieApi::class.java)
    }

}