package com.example.github.repositories.user_detail_repository_list.mapper

import com.example.github.repositories.common.local.OwnerLocalModel
import com.example.github.repositories.common.local.RepositoryLocalModel
import com.example.github.repositories.user_detail_repository_list.data_model.remote.UserRepositoryListRemote
import javax.inject.Inject

class UserRepositoryMapper @Inject constructor() :
    Function1<List<UserRepositoryListRemote>, List<RepositoryLocalModel>> {
    override fun invoke(remote: List<UserRepositoryListRemote>): List<RepositoryLocalModel> {
        val repositoryList = mutableListOf<RepositoryLocalModel>()
        for (repository in remote) {
            val owner = OwnerLocalModel(
                author = repository.owner.login,
                avatarUrl = repository.owner.avatar_url
            )
            repositoryList.add(
                RepositoryLocalModel(
                    name = repository.full_name,
                    description = repository.description,
                    repositoryId = repository.id,
                    isBookMarked = true,
                    owner = owner,
                    createdAt = repository.created_at,
                    htmlUrl = repository.html_url,
                )
            )
        }
        return repositoryList
    }

}
