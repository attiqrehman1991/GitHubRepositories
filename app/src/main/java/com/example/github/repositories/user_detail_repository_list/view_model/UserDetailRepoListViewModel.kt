package com.example.github.repositories.user_detail_repository_list.view_model

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.github.repositories.common.local.RepositoryLocalModel
import com.example.github.repositories.user_detail_repository_list.UserDetailRepositoryListRepository
import com.example.github.repositories.user_detail_repository_list.data_model.local.UserDetailLocal
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class UserDetailRepoListViewModel(
    private val repository: UserDetailRepositoryListRepository
) : ViewModel() {

    var owner: String = ""
    val userDetail = MutableLiveData<Result<UserDetailLocal>>()
    val loader = MutableLiveData<Boolean>()

    fun getUserDetail(author: String) {
        viewModelScope.launch {
            loader.postValue(true)
            repository.getUserDetail(author)
                .onEach {
                    loader.postValue(false)
                }.collect {
                    userDetail.postValue(it)
                }
        }
    }

    val repositories = MutableLiveData<Result<List<RepositoryLocalModel>>>()
    fun getRepositories(reposUrl: String) {
        viewModelScope.launch {
            loader.postValue(true)
            repository.getUserRepositoryList(reposUrl)
                .onEach {
                    loader.postValue(false)
                }
                .collect {
                    repositories.value = it
                }
        }
    }

    fun refresh() {
        getRepositories(owner)
    }

    fun updateBookmarkForRepository(repositoryId: Int, newStatus: Boolean) {
        viewModelScope.launch {
            repository.updateBookmark(repositoryId, newStatus)
        }
    }

}
