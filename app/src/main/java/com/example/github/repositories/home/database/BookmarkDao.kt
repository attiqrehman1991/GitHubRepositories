package com.example.github.repositories.home.database

import androidx.room.*
import com.example.github.repositories.home.data_model.room.Bookmark

@Dao
interface BookmarkDao {
    @Query("SELECT * FROM bookmark_table WHERE repositoryId IN (:repositoryIdList)")
    suspend fun getBookmarks(repositoryIdList:List<Int>): List<Bookmark>?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(bookmark: Bookmark)

    @Update
    suspend fun update(bookmark: Bookmark)

    @Delete
    suspend fun delete(bookmark: Bookmark)

    @Query("UPDATE bookmark_table SET isBookmarked = :newStatus WHERE repositoryId=:repositoryId")
    suspend fun updateRepository(repositoryId: Int, newStatus: Boolean)
}