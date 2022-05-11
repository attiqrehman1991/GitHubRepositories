package com.example.github.repositories.features.home_repository_list

import com.example.github.repositories.home.HomeRepositoryListAPI
import com.example.github.repositories.home.HomeRepositoryListService
import com.example.github.repositories.home.data_model.remote.RepositoryRemoteModel
import com.example.github.repositories.utils.BaseUnitTest
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test

class HomeRepositoryListServiceShould : BaseUnitTest() {

    private var repositoryList: RepositoryRemoteModel = mock()
    private var api: HomeRepositoryListAPI = mock()

    @Test
    fun fetchRepositoryListFromAPI() = runTest {
        val service = HomeRepositoryListService(api)
        service.fetchRepositoryList().first()
        verify(api, times(1)).fetchRepositoryList()
    }

    @Test
    fun convertValuesToFlowResultAndEmitsThen() = runTest {
        whenever(api.fetchRepositoryList()).thenReturn(repositoryList)
        val service = HomeRepositoryListService(api)
        assertEquals(Result.success(repositoryList), service.fetchRepositoryList().first())
    }

    @Test
    fun emitErrorResultWhenNetworkFails() = runTest {
        whenever(api.fetchRepositoryList()).thenThrow(RuntimeException("Something went wrong"))
        val service = HomeRepositoryListService(api)

        assertEquals(
            "Something went wrong",
            service.fetchRepositoryList().first().exceptionOrNull()?.message
        )
    }

}