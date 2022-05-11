package com.example.github.repositories.common.local

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class RepositoryLocalModel(
    val repositoryId: Int,
    val name: String,
    val description: String?,
    var isBookMarked: Boolean,
    val htmlUrl: String,
    val createdAt: String,
    val owner: OwnerLocalModel,
) : Parcelable

@Parcelize
data class OwnerLocalModel(
    val author: String,
    val avatarUrl: String?,
) : Parcelable