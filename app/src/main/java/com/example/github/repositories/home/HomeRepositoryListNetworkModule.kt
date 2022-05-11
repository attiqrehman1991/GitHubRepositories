package com.example.github.repositories.home

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.FragmentComponent
import retrofit2.Retrofit

@Module
@InstallIn(FragmentComponent::class)
class HomeRepositoryListNetworkModule {

    @Provides
    fun repositoryAPI(retrofit: Retrofit) =
        retrofit.create(HomeRepositoryListAPI::class.java)
}