package com.example.github.repositories.home.data_model.room

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity(tableName = "bookmark_table", indices = [Index(value = ["repositoryId"], unique = true)])
@Parcelize
data class Bookmark(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val repositoryId: Int = 0,
    val isBookmarked: Boolean = false
) : Parcelable