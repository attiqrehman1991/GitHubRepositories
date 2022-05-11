package com.example.github.repositories.home

import com.example.github.repositories.home.data_model.remote.RepositoryRemoteModel
import retrofit2.http.GET
import retrofit2.http.Query

interface HomeRepositoryListAPI {

    @GET("search/repositories")
    suspend fun fetchRepositoryList(
        @Query("q") q: String = "android+language:kotlin",
        @Query("sort") sort: String = "stars",
        @Query("order") order: String = "desc",
        @Query("per_page") perPage: Int = 20
    ): RepositoryRemoteModel

}
