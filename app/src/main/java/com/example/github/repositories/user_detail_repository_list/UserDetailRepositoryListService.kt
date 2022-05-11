package com.example.github.repositories.user_detail_repository_list

import com.example.github.repositories.user_detail_repository_list.data_model.remote.UserDetailRemote
import com.example.github.repositories.user_detail_repository_list.data_model.remote.UserRepositoryListRemote
import com.google.gson.JsonSyntaxException
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import javax.inject.Inject

class UserDetailRepositoryListService @Inject constructor(
    private val api: UserDetailRepositoryListApi
) {
    suspend fun getUserDetailFromService(userName: String): Flow<Result<UserDetailRemote>> {
        return flow {
            emit(Result.success(api.fetchUserDetail(username = userName)))
        }.catch { e ->
            when (e) {
                is SocketTimeoutException ->
                    emit(Result.failure(RuntimeException("Socket Time out")))
                is UnknownHostException ->
                    emit(Result.failure(RuntimeException("Network Issue")))
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

    suspend fun getUserRepositoryListFromService(repoUrl: String): Flow<Result<List<UserRepositoryListRemote>>> {
        return flow {
            val response = api.fetchRepositoryList(repoUrl)
            emit(Result.success(response))
        }.catch { e ->
//            emit(Result.failure(RuntimeException("Something went wrong")))
            when (e) {
                is SocketTimeoutException ->
                    emit(Result.failure(RuntimeException("Socket Time out")))
                is UnknownHostException ->
                    emit(Result.failure(RuntimeException("Unknown Host Exception")))
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