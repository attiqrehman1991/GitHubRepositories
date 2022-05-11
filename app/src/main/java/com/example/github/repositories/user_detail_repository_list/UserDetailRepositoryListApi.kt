package com.example.github.repositories.user_detail_repository_list

import com.example.github.repositories.user_detail_repository_list.data_model.remote.UserDetailRemote
import com.example.github.repositories.user_detail_repository_list.data_model.remote.UserRepositoryListRemote
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface UserDetailRepositoryListApi {

    @GET("users/{username}")
    suspend fun fetchUserDetail(
        @Path("username") username: String
    ): UserDetailRemote

    @GET("users/{username}/repos")
    suspend fun fetchRepositoryList(
        @Path("username") username: String,
        @Query("per_page") perPage: Int = 20
    ): List<UserRepositoryListRemote>

}