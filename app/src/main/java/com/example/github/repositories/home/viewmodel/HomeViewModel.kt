package com.example.github.repositories.home.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.github.repositories.home.HomeRepositoryListRepository
import com.example.github.repositories.common.local.RepositoryLocalModel
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class HomeViewModel(
    private val repository: HomeRepositoryListRepository
) : ViewModel() {
    val loader: MutableLiveData<Boolean> = MutableLiveData<Boolean>()

    val repositories = MutableLiveData<Result<List<RepositoryLocalModel>>>()
    init {
        getRepositories()
    }
    fun getRepositories() {
        viewModelScope.launch {
            loader.postValue(true)
            repository.getRepositoryList()
                .onEach {
                    loader.postValue(false)
                }
                .collect {
                    repositories.value = it
                }
        }
    }

    fun refresh() {
        getRepositories()
    }


    fun updateBookmarkForRepository(repositoryId: Int, newStatus: Boolean) {
        viewModelScope.launch {
            repository.updateBookmark(repositoryId, newStatus)
        }
    }
}