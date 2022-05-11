package com.example.github.repositories.features.user_detail_repository_list

import com.example.github.repositories.common.local.RepositoryLocalModel
import com.example.github.repositories.common.module.network_connectivity.NetworkConnection
import com.example.github.repositories.home.HomeRepositoryListRoom
import com.example.github.repositories.home.business_logic.MapperRepositoryListForBookmarks
import com.example.github.repositories.user_detail_repository_list.UserDetailRepositoryListRepository
import com.example.github.repositories.user_detail_repository_list.UserDetailRepositoryListService
import com.example.github.repositories.user_detail_repository_list.mapper.UserRepositoryMapper
import com.example.github.repositories.user_detail_repository_list.data_model.local.UserDetailLocal
import com.example.github.repositories.user_detail_repository_list.data_model.remote.UserDetailRemote
import com.example.github.repositories.user_detail_repository_list.data_model.remote.UserRepositoryListRemote
import com.example.github.repositories.user_detail_repository_list.mapper.UserDetailMapper
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

class UserDetailRepositoryListRepositoryShould : BaseUnitTest() {

    private val service: UserDetailRepositoryListService = mock()
    private val room: HomeRepositoryListRoom = mock()
    private val expectedUserDetail = mock<UserDetailLocal>()
    private val remoteUserDetail = mock<UserDetailRemote>()
    private val networkConnection: NetworkConnection = mock()
    private val mapperBookmarks: HashMap<Int, Boolean> = mock()
    private val mapperForUserDetail: UserDetailMapper = mock()
    private val username = "SquareDev"
    private val mapperRepoListForBookmarks: MapperRepositoryListForBookmarks = mock()

    private val repositoryList = mock<List<RepositoryLocalModel>>()
    private val remoteRepoListData = mock<List<UserRepositoryListRemote>>()
    private val mapperForRepoList: UserRepositoryMapper = mock()
    private val usersRepositoryUrl = "https://api.github.com/users/square/repos"

    @Test
    fun getUserDetailFromService() = runTest {
        val repository = mockUserDetailSuccessfulCase()
        repository.getUserDetail(username)
        verify(service, times(1)).getUserDetailFromService(username);
    }

    @Test
    fun emitUserDetailFromService() = runTest {
        val repository = mockUserDetailSuccessfulCase()
        assertEquals(expectedUserDetail, repository.getUserDetail(username).first().getOrNull())
    }

    @Test
    fun propagateErrorsForUserDetail() = runTest {
        val repository = mockUserDetailFailureCase()
        assertEquals(exception, repository.getUserDetail(username).first().exceptionOrNull()!!)
    }

    @Test
    fun delegateBusinessLogicForUserDetail() = runTest {
        val repository = mockUserDetailSuccessfulCase()
        repository.getUserDetail(username).first()

        verify(mapperForUserDetail, times(1)).invoke(remoteUserDetail)
    }

    private suspend fun mockUserDetailFailureCase(): UserDetailRepositoryListRepository {
        whenever(service.getUserDetailFromService(username)).thenReturn(
            flow {
                emit(Result.failure<UserDetailRemote>(exception))
            }
        )
        return UserDetailRepositoryListRepository(
            service,
            mapperForUserDetail,
            mapperForRepoList,
            mapperRepoListForBookmarks,
            room,
            networkConnection
        )
    }

    private suspend fun mockUserDetailSuccessfulCase(): UserDetailRepositoryListRepository {
        whenever(service.getUserDetailFromService(username)).thenReturn(
            flow {
                emit(Result.success(remoteUserDetail))
            }
        )
        whenever(mapperForUserDetail.invoke(remoteUserDetail)).thenReturn(expectedUserDetail)
        return UserDetailRepositoryListRepository(
            service,
            mapperForUserDetail,
            mapperForRepoList,
            mapperRepoListForBookmarks,
            room,
            networkConnection
        )
    }

    // For repository List

    @Test
    fun getRepositoryListFromService() = runTest {
        val repository = mockRepositoryListSuccessfulCase()
        repository.getUserRepositoryList(usersRepositoryUrl)

        verify(service, times(1)).getUserRepositoryListFromService(usersRepositoryUrl)
    }

    @Test
    fun emitRepositoryListFromService() = runTest {
        val repository = mockRepositoryListSuccessfulCase()
        assertEquals(
            repositoryList,
            repository.getUserRepositoryList(usersRepositoryUrl).first().getOrNull()
        )
    }

    @Test
    fun propagateErrorsForRepositoryList() = runTest {
        val repository = mockRepositoryListFailedCase()
        assertEquals(
            exception,
            repository.getUserRepositoryList(usersRepositoryUrl).first().exceptionOrNull()!!
        )
    }

    @Test
    fun delegateBusinessLogicForRepositoryList() = runTest {
        val repository = mockRepositoryListSuccessfulCase()
        repository.getUserRepositoryList(usersRepositoryUrl).first()

        verify(mapperForRepoList, times(1)).invoke(remoteRepoListData)
    }

    @Test
    fun delegateBusinessLogicRepoListToMapperBookmarks() = runTest {
        val repository = mockRepositoryListSuccessfulCase()
        repository.getUserRepositoryList(usersRepositoryUrl).first()

        verify(mapperRepoListForBookmarks, times(1)).invoke(repositoryList, mapperBookmarks)
    }

    private suspend fun mockRepositoryListFailedCase(): UserDetailRepositoryListRepository {
        whenever(service.getUserRepositoryListFromService(usersRepositoryUrl)).thenReturn(
            flow {
                emit(Result.failure<List<UserRepositoryListRemote>>(exception))
            }
        )
        return UserDetailRepositoryListRepository(
            service,
            mapperForUserDetail,
            mapperForRepoList,
            mapperRepoListForBookmarks,
            room,
            networkConnection
        )
    }

    private suspend fun mockRepositoryListSuccessfulCase(): UserDetailRepositoryListRepository {
        whenever(service.getUserRepositoryListFromService(usersRepositoryUrl)).thenReturn(
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
        return UserDetailRepositoryListRepository(
            service,
            mapperForUserDetail,
            mapperForRepoList,
            mapperRepoListForBookmarks,
            room,
            networkConnection
        )
    }
}