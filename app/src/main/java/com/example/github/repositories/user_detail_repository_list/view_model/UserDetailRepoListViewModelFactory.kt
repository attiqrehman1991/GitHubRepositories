package com.example.github.repositories.user_detail_repository_list.view_model

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.github.repositories.user_detail_repository_list.UserDetailRepositoryListRepository
import javax.inject.Inject

@Suppress("UNCHECKED_CAST")
class UserDetailRepoListViewModelFactory @Inject constructor(
    private val repository: UserDetailRepositoryListRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return UserDetailRepoListViewModel(repository) as T
    }
}
