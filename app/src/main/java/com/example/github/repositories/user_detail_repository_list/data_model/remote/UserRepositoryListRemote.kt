package com.example.github.repositories.user_detail_repository_list.data_model.remote

data class UserRepositoryListRemote(
    val description: String,
    val full_name: String,
    val owner: Owner,
    val created_at: String,
    val html_url: String,
    val id: Int
)

data class Owner(
    val login: String,
    val avatar_url: String,
)