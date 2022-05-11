package com.example.github.repositories.user_detail_repository_list

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.FragmentComponent
import retrofit2.Retrofit

@Module
@InstallIn(FragmentComponent::class)
class UserDetailAndRepositoryNetworkModule {

    @Provides
    fun userDetailRepositoryListAPI(retrofit: Retrofit) =
        retrofit.create(UserDetailRepositoryListApi::class.java)
}