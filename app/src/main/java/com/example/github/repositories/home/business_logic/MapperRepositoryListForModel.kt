package com.example.github.repositories.home.business_logic

import com.example.github.repositories.common.local.OwnerLocalModel
import com.example.github.repositories.common.local.RepositoryLocalModel
import com.example.github.repositories.home.data_model.remote.RepositoryRemoteModel
import javax.inject.Inject

class MapperRepositoryListForModel @Inject constructor() :
    Function1<RepositoryRemoteModel, List<RepositoryLocalModel>> {
    override fun invoke(remote: RepositoryRemoteModel): List<RepositoryLocalModel> {
        val repositoryList = mutableListOf<RepositoryLocalModel>()
        for (repository in remote.items!!) {
            val owner = OwnerLocalModel(
                author = repository.owner.login,
                avatarUrl = repository.owner.avatar_url
            )
            repositoryList.add(
                RepositoryLocalModel(
                    repositoryId = repository.id,
                    name = repository.full_name,
                    description = repository.description,
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
