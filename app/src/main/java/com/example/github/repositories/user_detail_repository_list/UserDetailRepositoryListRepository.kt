package com.example.github.repositories.user_detail_repository_list

import com.example.github.repositories.common.local.RepositoryLocalModel
import com.example.github.repositories.common.module.network_connectivity.NetworkConnection
import com.example.github.repositories.home.HomeRepositoryListRoom
import com.example.github.repositories.home.business_logic.MapperRepositoryListForBookmarks
import com.example.github.repositories.user_detail_repository_list.data_model.local.UserDetailLocal
import com.example.github.repositories.user_detail_repository_list.mapper.UserDetailMapper
import com.example.github.repositories.user_detail_repository_list.mapper.UserRepositoryMapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class UserDetailRepositoryListRepository @Inject constructor(
    private val service: UserDetailRepositoryListService,
    private val mapperUserDetail: UserDetailMapper,
    private val mapperForRepoList: UserRepositoryMapper,
    private val mapperBookmarks: MapperRepositoryListForBookmarks,
    private val room: HomeRepositoryListRoom,
    private val networkConnection: NetworkConnection
) {
    suspend fun getUserDetail(username: String): Flow<Result<UserDetailLocal>> {
        if (networkConnection.isConnected()) {
            return service.getUserDetailFromService(username).map {
                if (it.isSuccess)
                    Result.success(mapperUserDetail(it.getOrNull()!!))
                else
                    Result.failure(it.exceptionOrNull()!!)
            }
        } else {
            return flow {
                emit(Result.failure(RuntimeException("Network Issue")))
            }
        }
    }

    suspend fun getUserRepositoryList(repoUrl: String): Flow<Result<List<RepositoryLocalModel>>> {
        if (networkConnection.isConnected()) {
            return service.getUserRepositoryListFromService(repoUrl).map {
                if (it.isSuccess) {
                    val allFetchedRositories = mapperForRepoList(it.getOrNull()!!)
                    val hashBookmarks =
                        room.getMappedBookmarksForRepositoryList(allFetchedRositories)
                    Result.success(mapperBookmarks(allFetchedRositories, hashBookmarks))
                } else
                    Result.failure(it.exceptionOrNull()!!)
            }
        } else {
            return flow {
                emit(Result.failure(RuntimeException("Network Issue")))
            }
        }
    }

    suspend fun updateBookmark(repositoryId: Int, newStatus: Boolean) {
        room.updateBookmark(repositoryId, newStatus)
    }
}

