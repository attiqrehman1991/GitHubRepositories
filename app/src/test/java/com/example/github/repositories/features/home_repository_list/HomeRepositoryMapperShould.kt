package com.example.github.repositories.features.home_repository_list

import com.example.github.repositories.home.business_logic.MapperRepositoryListForModel
import com.example.github.repositories.home.data_model.remote.Item
import com.example.github.repositories.home.data_model.remote.Owner
import com.example.github.repositories.home.data_model.remote.RepositoryRemoteModel
import org.junit.Assert.assertEquals
import org.junit.Test

class HomeRepositoryMapperShould {
    private val mapper = MapperRepositoryListForModel()

    val owner = Owner(login = "square")
    val item = listOf(Item(id=1, description = "quare’s meticulous HTTP client for the JVM, Android, and GraalVM.", full_name = "square/okhttp", owner = owner, created_at = "2019-01-21T03:45:40Z", html_url = "https://github.com/shadowsocks/android-ndk-go"))


    private val remoteModel = RepositoryRemoteModel(items = item, incomplete_results = false, total_count = 1)
    private val localRepositoryList = mapper(remoteModel)

    @Test
    fun keepSameDescription() {
        assertEquals(remoteModel.items!![0].description, localRepositoryList[0].description)
    }

    @Test
    fun keepSameOwner() {
        assertEquals(remoteModel.items!![0].full_name, localRepositoryList[0].name)
    }

    @Test
    fun keepSameAuthor() {
        assertEquals(remoteModel.items!![0].owner.login, localRepositoryList[0].owner.author)
    }
}