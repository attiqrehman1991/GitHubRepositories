package com.example.github.repositories.home

import com.example.github.repositories.common.module.network_connectivity.NetworkConnection
import com.example.github.repositories.home.business_logic.MapperRepositoryListForBookmarks
import com.example.github.repositories.home.business_logic.MapperRepositoryListForModel
import com.example.github.repositories.common.local.RepositoryLocalModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class HomeRepositoryListRepository @Inject constructor(
    private val service: HomeRepositoryListService,
    private val mapperForList: MapperRepositoryListForModel,
    private val mapperBookmarks: MapperRepositoryListForBookmarks,
    private val room: HomeRepositoryListRoom,
    private val networkConnection: NetworkConnection
) {

    suspend fun getRepositoryList(): Flow<Result<List<RepositoryLocalModel>>> {
        if (networkConnection.isConnected()) {
            return service.fetchRepositoryList().map { resultFromApi ->
                if (resultFromApi.isSuccess) {
                    val allFetchedRepositories = mapperForList(resultFromApi.getOrNull()!!)
                    val hashBookmarks =
                        room.getMappedBookmarksForRepositoryList(allFetchedRepositories)
                    Result.success(mapperBookmarks(allFetchedRepositories, hashBookmarks))
                } else
                    Result.failure(resultFromApi.exceptionOrNull()!!)
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
