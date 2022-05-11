package com.example.github.repositories.features.user_detail_repository_list

import com.example.github.repositories.common.local.RepositoryLocalModel
import com.example.github.repositories.user_detail_repository_list.UserDetailRepositoryListRepository
import com.example.github.repositories.user_detail_repository_list.data_model.local.UserDetailLocal
import com.example.github.repositories.user_detail_repository_list.view_model.UserDetailRepoListViewModel
import com.example.github.repositories.utils.BaseUnitTest
import com.example.github.repositories.utils.captureValues
import com.example.github.repositories.utils.getValueForTest
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test

class UserDetailRepositoryListViewModelShould : BaseUnitTest() {

    private val repository: UserDetailRepositoryListRepository = mock()

    private val userDetail = mock<UserDetailLocal>()
    private val expectedUserDetail = Result.success(userDetail)
    private val username = "SquareDev"


    private val repositoryList = mock<List<RepositoryLocalModel>>()
    private val expectedRepositoryList = Result.success(repositoryList)
    private val usersRepositoryUrl = "https://api.github.com/users/square/repos"

    @Test
    fun getUserDetailFromRepository() = runTest {
        val viewModel = mockUserDetailSuccessfulCase()

        viewModel.getUserDetail(username)
        viewModel.userDetail.getValueForTest()

        verify(repository, times(1)).getUserDetail(username)
    }

    @Test
    fun emitUserDetailFromRepository() = runTest {
        val viewModel = mockUserDetailSuccessfulCase()

        viewModel.getUserDetail(username)
        assertEquals(expectedUserDetail, viewModel.userDetail.getValueForTest())
    }

    @Test
    fun emitErrorWhenReceivedErrorForUSerDetail() {
        val viewModel = mockUserDetailFailureCase()
        viewModel.getUserDetail(username)
        assertEquals(exception, viewModel.userDetail.getValueForTest()!!.exceptionOrNull())
    }

    @Test
    fun showSpinnerWhileLoaderForUserDetail() = runBlockingTest {
        val viewModel = mockUserDetailSuccessfulCase()
        viewModel.loader.captureValues {
            viewModel.getUserDetail(username)
            viewModel.userDetail.getValueForTest()

            assertEquals(true, values[0])
        }
    }

    @Test
    fun closeSpinnerAfterLoaderForUserDetail() = runBlockingTest {
        val viewModel = mockUserDetailSuccessfulCase()
        viewModel.loader.captureValues {
            viewModel.getUserDetail(username)
            viewModel.userDetail.getValueForTest()

            assertEquals(false, values.last())
        }
    }

    @Test
    fun closeSpinnerAfterErrorForUserDEtail() = runBlockingTest {
        val viewModel = mockUserDetailFailureCase()
        viewModel.loader.captureValues {
            viewModel.getUserDetail(username)
            viewModel.userDetail.getValueForTest()

            assertEquals(false, values.last())
        }
    }

    private fun mockUserDetailSuccessfulCase(): UserDetailRepoListViewModel {
        runBlocking {
            whenever(repository.getUserDetail(username)).thenReturn(
                flow {
                    emit(expectedUserDetail)
                }
            )
        }
        return UserDetailRepoListViewModel(repository)
    }

    private fun mockUserDetailFailureCase(): UserDetailRepoListViewModel {
        runBlocking {
            whenever(repository.getUserDetail(username)).thenReturn(
                flow {
                    emit(Result.failure<UserDetailLocal>(exception))
                }
            )
        }
        return UserDetailRepoListViewModel(repository)
    }

    @Test
    fun getRepositoryListFromRepository() = runTest {
        val viewModel = UserDetailRepoListViewModel(repository)

        viewModel.getRepositories(usersRepositoryUrl)
        viewModel.repositories.getValueForTest()

        verify(repository, times(1)).getUserRepositoryList(usersRepositoryUrl)
    }

    @Test
    fun emitRepositoryListFromRepository() = runBlocking {
        val viewModel = mockRepositoryListSuccessfulCase()
        viewModel.getRepositories(usersRepositoryUrl)
        assertEquals(expectedRepositoryList, viewModel.repositories.getValueForTest())
    }

    @Test
    fun emitErrorWhenReceiveError() = runBlocking {
        val viewModel = mockRepositoryListFailureCase()

        viewModel.getRepositories(usersRepositoryUrl)
        assertEquals(exception, viewModel.repositories.getValueForTest()!!.exceptionOrNull())
    }

    @Test
    fun showSpinnerWhileLoaderForRepositoryList() = runBlockingTest {
        val viewModel = mockRepositoryListSuccessfulCase()
        viewModel.loader.captureValues {
            viewModel.getRepositories(usersRepositoryUrl)
            viewModel.repositories.getValueForTest()

            assertEquals(true, values[0])
        }
    }

    @Test
    fun closeSpinnerAfterLoaderForRepositoryList() = runBlockingTest {
        val viewModel = mockRepositoryListSuccessfulCase()
        viewModel.loader.captureValues {
            viewModel.getRepositories(usersRepositoryUrl)
            viewModel.repositories.getValueForTest()

            assertEquals(false, values.last())
        }
    }

    @Test
    fun closeSpinnerAfterErrorForRepositoryList() = runBlockingTest {
        val viewModel = mockRepositoryListFailureCase()
        viewModel.loader.captureValues {
            viewModel.getRepositories(usersRepositoryUrl)
            viewModel.repositories.getValueForTest()

            assertEquals(false, values.last())
        }
    }

    private fun mockRepositoryListSuccessfulCase(): UserDetailRepoListViewModel {
        runBlocking {
            whenever(repository.getUserRepositoryList(usersRepositoryUrl)).thenReturn(
                flow {
                    emit(expectedRepositoryList)
                }
            )
        }
        return UserDetailRepoListViewModel(repository)
    }

    private fun mockRepositoryListFailureCase(): UserDetailRepoListViewModel {
        runBlocking {
            whenever(repository.getUserRepositoryList(usersRepositoryUrl)).thenReturn(
                flow {
                    emit(Result.failure<List<RepositoryLocalModel>>(exception))
                }
            )
        }
        return UserDetailRepoListViewModel(repository)
    }
}