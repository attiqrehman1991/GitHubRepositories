package com.example.github.repositories.home

import com.example.github.repositories.common.local.RepositoryLocalModel
import com.example.github.repositories.home.data_model.room.Bookmark
import com.example.github.repositories.home.database.BookmarkDao
import javax.inject.Inject

class HomeRepositoryListRoom @Inject constructor(
    private val bookmarkDao: BookmarkDao
) {
    suspend fun updateBookmark(repositoryId: Int, newStatus: Boolean) {
        val bookmark = Bookmark(repositoryId = repositoryId, isBookmarked = newStatus)
        bookmarkDao.insert(bookmark)
    }

    suspend fun getMappedBookmarksForRepositoryList(listOfRepositoryIds: List<RepositoryLocalModel>): HashMap<Int, Boolean> {
        val listOfRepositoryId = mutableListOf<Int>()
        for (repo in listOfRepositoryIds)
            listOfRepositoryId.add(repo.repositoryId)
        val bookmarks = bookmarkDao.getBookmarks(listOfRepositoryId)
        val hashMap = hashMapOf<Int, Boolean>()
        for (bookmark in bookmarks!!)
            hashMap[bookmark.repositoryId] = bookmark.isBookmarked
        return hashMap
    }

}