package com.example.github.repositories.common.module.network_client

import com.example.github.repositories.BuildConfig
import com.example.github.repositories.data.GITHUB_URL
import com.google.gson.GsonBuilder
import com.jakewharton.espresso.OkHttp3IdlingResource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.FragmentComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val loggingInterceptor = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
var client: OkHttpClient = OkHttpClient.Builder()
    .addInterceptor(loggingInterceptor)
    .build()
val idlingResource = OkHttp3IdlingResource.create("OkHttp", client)

@Module
@InstallIn(FragmentComponent::class)
class RepositoryNetworkModule {

    @Provides
    fun retrofit(): Retrofit =
        Retrofit.Builder()
            .baseUrl(GITHUB_URL)
            .client(if (BuildConfig.DEBUG) client else OkHttpClient())
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
            .build()
}