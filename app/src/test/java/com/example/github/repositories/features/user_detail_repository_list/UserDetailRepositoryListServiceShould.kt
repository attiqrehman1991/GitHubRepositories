package com.example.github.repositories.features.user_detail_repository_list

import com.example.github.repositories.user_detail_repository_list.UserDetailRepositoryListApi
import com.example.github.repositories.user_detail_repository_list.UserDetailRepositoryListService
import com.example.github.repositories.user_detail_repository_list.data_model.remote.UserDetailRemote
import com.example.github.repositories.user_detail_repository_list.data_model.remote.UserRepositoryListRemote
import com.example.github.repositories.utils.BaseUnitTest
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test

class UserDetailRepositoryListServiceShould : BaseUnitTest() {

    private val api: UserDetailRepositoryListApi = mock()
    private val expectedUserDetail: UserDetailRemote = mock()
    private val username = "SquareDev"

    private var repositoryList: List<UserRepositoryListRemote> = mock()
    private val usersRepositoryUrl = "https://api.github.com/users/square/repos"

    @Test
    fun fetchUserDetailFromAPI() = runTest {
        val service = UserDetailRepositoryListService(api)
        service.getUserDetailFromService(userName = username).first()
        verify(api, times(1)).fetchUserDetail(username = username)
    }

    @Test
    fun convertValuesToFlowResultAndEmitThenForUserDetail() = runTest {
        val service = mockUserDetailSuccessfulCase()
        assertEquals(
            Result.success(expectedUserDetail),
            service.getUserDetailFromService(username).first()
        )
    }

    @Test
    fun emitErrorResultWhenNetworkFailsForUserDetails() = runTest {
        val service = mockUserDetailFailureCase()
        assertEquals(
            "Something went wrong",
            service.getUserDetailFromService(username).first().exceptionOrNull()?.message
        )
    }

    private fun mockUserDetailSuccessfulCase(): UserDetailRepositoryListService {
        runBlocking {
            whenever(api.fetchUserDetail(username)).thenReturn(expectedUserDetail)
        }
        return UserDetailRepositoryListService(api)
    }

    private fun mockUserDetailFailureCase(): UserDetailRepositoryListService {
        runBlocking {
            whenever(api.fetchUserDetail(username)).thenThrow(RuntimeException("Something went wrong"))
        }
        return UserDetailRepositoryListService(api)
    }

    // for repo list

    @Test
    fun fetchUserRepositoryListFromAPI() = runTest {
        val service = UserDetailRepositoryListService(api)
        service.getUserRepositoryListFromService(usersRepositoryUrl).first()
        verify(api, times(1)).fetchRepositoryList(usersRepositoryUrl)
    }

    @Test
    fun convertValuesToFlowResultAndEmitThenForRepositoryList() = runTest {
        whenever(api.fetchRepositoryList(usersRepositoryUrl)).thenReturn(repositoryList)
        val service = UserDetailRepositoryListService(api)

        assertEquals(
            Result.success(repositoryList),
            service.getUserRepositoryListFromService(usersRepositoryUrl).first()
        )
    }

    @Test
    fun emitErrorResultWhenNetworkFailsForUserRepositoryList() = runTest {
        whenever(api.fetchRepositoryList(usersRepositoryUrl)).thenThrow(RuntimeException("Something went wrong"))
        val service = UserDetailRepositoryListService(api)

        assertEquals(
            "Something went wrong",
            service.getUserRepositoryListFromService(usersRepositoryUrl).first()
                .exceptionOrNull()?.message
        )

//        val service = mockRepositoryListFailureCase()
//        assertEquals(
//            "Something went wrong",
//            service.getUserRepositoryListFromService(usersRepositoryUrl).first()
//                .exceptionOrNull()?.message
//        )
    }

//    private fun mockRepositoryListFailureCase() = runBlockingTest {
//        whenever(api.fetchRepositoryList(usersRepositoryUrl)).thenThrow(RuntimeException("Something went wrong"))
//        service = UserDetailRepositoryListService(api)
//    }
}