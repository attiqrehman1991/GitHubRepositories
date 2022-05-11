package com.example.github.repositories.common.module.network_connectivity

import android.app.Application
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class InternetConnectivityModule {

    @Provides
    fun checkNetworkConnectivity(context: Application): NetworkConnection =
        CheckNetowrkConnectivity(context)
}