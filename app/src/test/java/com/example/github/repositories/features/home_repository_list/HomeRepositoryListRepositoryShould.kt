package com.example.github.repositories.features.home_repository_list

import com.example.github.repositories.common.local.RepositoryLocalModel
import com.example.github.repositories.common.module.network_connectivity.NetworkConnection
import com.example.github.repositories.home.HomeRepositoryListRepository
import com.example.github.repositories.home.HomeRepositoryListRoom
import com.example.github.repositories.home.HomeRepositoryListService
import com.example.github.repositories.home.business_logic.MapperRepositoryListForBookmarks
import com.example.github.repositories.home.business_logic.MapperRepositoryListForModel
import com.example.github.repositories.home.data_model.remote.RepositoryRemoteModel
import com.example.github.repositories.utils.BaseUnitTest
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test

class HomeRepositoryListRepositoryShould : BaseUnitTest() {

    private val service: HomeRepositoryListService = mock()
    private val room: HomeRepositoryListRoom = mock()
    private val networkConnection: NetworkConnection = mock()
    private val repositoryList = mock<List<RepositoryLocalModel>>()
    private val remoteRepoListData = mock<RepositoryRemoteModel>()
    private val mapperForRepoList: MapperRepositoryListForModel = mock()
    private val mapperBookmarks: HashMap<Int, Boolean> = mock()
    private val mapperRepoListForBookmarks: MapperRepositoryListForBookmarks = mock()

    @Test
    fun getRepositoryFromService() = runTest {
        val repository = mockSuccessfulCase()
        repository.getRepositoryList()

        verify(service, times(1)).fetchRepositoryList()
    }

    @Test
    fun emitRepositoryListFromService() = runTest {
        val repository = mockSuccessfulCase()
        assertEquals(repositoryList, repository.getRepositoryList().first().getOrNull())
    }

    @Test
    fun propagateErrors() = runTest {
        val repository = mockFailedCase()
        assertEquals(exception, repository.getRepositoryList().first().exceptionOrNull())
    }

    @Test
    fun delegateBusinessLogicForRepoListToMapper() = runTest {
        val repository = mockSuccessfulCase()
        repository.getRepositoryList().first()

        verify(mapperForRepoList, times(1)).invoke(remoteRepoListData)
    }

    @Test
    fun delegateBusinessLogicRepoListToMapperBookmarks() = runTest {
        val repository = mockSuccessfulCase()
        repository.getRepositoryList().first()

        verify(mapperRepoListForBookmarks, times(1)).invoke(repositoryList, mapperBookmarks)
    }

    private suspend fun mockFailedCase(): HomeRepositoryListRepository {
        whenever(service.fetchRepositoryList()).thenReturn(
            flow {
                emit(Result.failure<RepositoryRemoteModel>(exception))
            }
        )
        return HomeRepositoryListRepository(
            service,
            mapperForRepoList,
            mapperRepoListForBookmarks,
            room,
            networkConnection
        )
    }

    private suspend fun mockSuccessfulCase(): HomeRepositoryListRepository {
        whenever(service.fetchRepositoryList()).thenReturn(
            flow {
                emit(Result.success(remoteRepoListData))
            }
        )
        whenever(mapperForRepoList.invoke(remoteRepoListData)).thenReturn(repositoryList)
        whenever(room.getMappedBookmarksForRepositoryList(repositoryList)).thenReturn(
            mapperBookmarks
        )
        whenever(mapperRepoListForBookmarks.invoke(repositoryList, mapperBookmarks)).thenReturn(
            repositoryList
        )
        return HomeRepositoryListRepository(
            service,
            mapperForRepoList,
            mapperRepoListForBookmarks,
            room,
            networkConnection
        )
    }
}