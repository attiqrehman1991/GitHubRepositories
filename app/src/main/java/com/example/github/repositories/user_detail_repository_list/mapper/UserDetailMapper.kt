package com.example.github.repositories.user_detail_repository_list.mapper

import com.example.github.repositories.user_detail_repository_list.data_model.local.UserDetailLocal
import com.example.github.repositories.user_detail_repository_list.data_model.remote.UserDetailRemote
import javax.inject.Inject

class UserDetailMapper @Inject constructor() :
    Function1<UserDetailRemote, UserDetailLocal> {
    override fun invoke(remote: UserDetailRemote): UserDetailLocal {
        val userDetailLocal = UserDetailLocal(
            twitterUsername = remote.twitter_username,
            reposUrl = remote.repos_url
        )
        return userDetailLocal
    }

}
