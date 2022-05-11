package com.example.github.repositories.home

import com.example.github.repositories.home.data_model.remote.RepositoryRemoteModel
import com.google.gson.JsonSyntaxException
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import javax.inject.Inject

class HomeRepositoryListService @Inject constructor(
    private val api: HomeRepositoryListAPI
) {
    suspend fun fetchRepositoryList(): Flow<Result<RepositoryRemoteModel>> {
        return flow {
            val response = api.fetchRepositoryList()
            emit(Result.success(response))
        }.catch { e ->
            when (e) {
                is SocketTimeoutException ->
                    emit(Result.failure(RuntimeException("Socket Time out")))
                is UnknownHostException ->
                    emit(Result.failure(RuntimeException("Something went wrong")))
                is JsonSyntaxException ->
                    emit(Result.failure(RuntimeException("Parsing problem")))
                is ConnectException ->
                    emit(Result.failure(RuntimeException("Connect Exception")))
                is HttpException ->
                    emit(Result.failure(RuntimeException("Http Exception")))
                else ->
                    emit(Result.failure(RuntimeException("Something went wrong")))
            }

        }
    }

}
