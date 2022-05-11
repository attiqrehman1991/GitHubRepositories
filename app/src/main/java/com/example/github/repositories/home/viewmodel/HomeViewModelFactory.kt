package com.example.github.repositories.home.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.github.repositories.common.module.network_connectivity.NetworkConnection
import com.example.github.repositories.home.HomeRepositoryListRepository
import javax.inject.Inject

@Suppress("UNCHECKED_CAST")
class HomeViewModelFactory @Inject constructor(
    private val repository: HomeRepositoryListRepository,
    private val networkConnection: NetworkConnection
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return HomeViewModel(repository) as T
    }
}
