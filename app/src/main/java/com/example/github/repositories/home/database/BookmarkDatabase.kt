package com.example.github.repositories.home.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.github.repositories.home.data_model.room.Bookmark
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Provider

@Database(entities = [Bookmark::class], version = 1)
abstract class BookmarkDatabase : RoomDatabase() {
    abstract fun bookmarkDao() : BookmarkDao

    class Callback @Inject constructor(
        private val database: Provider<BookmarkDatabase>,
        private val applicationScope : CoroutineScope
    ) : RoomDatabase.Callback() {
        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)

//            val dao = database.get().bookmarkDao()

            applicationScope.launch {
//                dao.insert(Bookmark(repositoryId = 1, isBookmarked = false))
//                dao.insert(Bookmark(repositoryId = 2, isBookmarked = false))
//                dao.insert(Bookmark(repositoryId = 3, isBookmarked = false))
//                dao.insert(Bookmark(repositoryId = 4, isBookmarked = false))
//                dao.insert(Bookmark(repositoryId = 5, isBookmarked = false))
            }

//            GlobalScope.
//            dao.insert()
        }
    }
}