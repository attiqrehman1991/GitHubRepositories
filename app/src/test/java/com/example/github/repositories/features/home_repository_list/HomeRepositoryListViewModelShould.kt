package com.example.github.repositories.features.home_repository_list

import com.example.github.repositories.common.local.RepositoryLocalModel
import com.example.github.repositories.home.HomeRepositoryListRepository
import com.example.github.repositories.home.viewmodel.HomeViewModel
import com.example.github.repositories.utils.BaseUnitTest
import com.example.github.repositories.utils.captureValues
import com.example.github.repositories.utils.getValueForTest
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test

class HomeRepositoryListViewModelShould : BaseUnitTest() {

    private val repository: HomeRepositoryListRepository = mock()

    private val repositoryList = mock<List<RepositoryLocalModel>>()
    private val expected = Result.success(repositoryList)

    @Test
    fun getRepositoryListFromRepository() = runTest {
        val viewModel = mockSuccessfulCase()

//        viewModel.getRepositories()
        viewModel.repositories.getValueForTest()

        verify(repository, times(1)).getRepositoryList()
    }

    @Test
    fun emitRepositoryListFromRepository() = runTest {
        val viewModel = mockSuccessfulCase()
        viewModel.getRepositories()
        assertEquals(expected, viewModel.repositories.getValueForTest())
    }

    @Test
    fun emitErrorWhenReceiveError() = runTest {
        val viewModel = mockFailedCase()

        viewModel.getRepositories()
        assertEquals(exception, viewModel.repositories.getValueForTest()!!.exceptionOrNull())
    }

    private suspend fun mockFailedCase(): HomeViewModel {
        whenever(repository.getRepositoryList()).thenReturn(
            flow {
                emit(Result.failure<List<RepositoryLocalModel>>(exception))
            }
        )
        return HomeViewModel(repository)
    }

    private suspend fun mockSuccessfulCase(): HomeViewModel {
        whenever(repository.getRepositoryList()).thenReturn(
            flow {
                emit(expected)
            }
        )
        return HomeViewModel(repository)
    }

    @Test
    fun showSpinnerWhileLoading() = runTest {
        val viewModel = mockSuccessfulCase()
        viewModel.loader.captureValues {
//            viewModel.getRepositories()
            viewModel.repositories.getValueForTest()

            assertEquals(false, values[0])
        }
    }

    @Test
    fun closeLoaderAfterLoading() = runTest {
        val viewModel = mockSuccessfulCase()
        viewModel.loader.captureValues {
//            viewModel.getRepositories()
            viewModel.repositories.getValueForTest()

            assertEquals(false, values.last())
        }
    }

    @Test
    fun closeLoaderAfterError() = runTest {
        val viewModel = mockFailedCase()
        viewModel.loader.captureValues {
            viewModel.getRepositories()
            viewModel.repositories.getValueForTest()

            assertEquals(false, values.last())
        }
    }

}