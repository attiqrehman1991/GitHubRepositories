package com.example.github.repositories.features.user_detail_repository_list

import com.example.github.repositories.user_detail_repository_list.data_model.remote.UserDetailRemote
import com.example.github.repositories.user_detail_repository_list.mapper.UserDetailMapper
import org.junit.Assert.assertEquals
import org.junit.Test

class UserDetailRepositoryMapperShould {
    private val mapper = UserDetailMapper()

    val userDetailRemote = UserDetailRemote(
        twitter_username = "SquareDev",
        repos_url = "https://api.github.com/users/square/repos"
    )
    private val userDetailLocal = mapper(userDetailRemote)

    @Test
    fun keepRepoUrl() {
        assertEquals(userDetailLocal.reposUrl, userDetailRemote.repos_url)
    }

    @Test
    fun keepTwitterUsername() {
        assertEquals(userDetailLocal.twitterUsername, userDetailRemote.twitter_username)
    }
}