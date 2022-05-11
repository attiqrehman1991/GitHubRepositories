package com.example.github.repositories.home.business_logic

import com.example.github.repositories.common.local.OwnerLocalModel
import com.example.github.repositories.common.local.RepositoryLocalModel
import javax.inject.Inject

//class MapperRepoListForBookmarks @Inject constructor(
//    private val bookmarkDao: BookmarkDao
//) :
//    Function1<List<RepositoryLocalModel>, List<RepositoryLocalModel>> {
//    override fun invoke(remoteRefactoredData: List<RepositoryLocalModel>): List<RepositoryLocalModel> {
//        val repositoryList = mutableListOf<RepositoryLocalModel>()
//
//        GlobalScope.launch(Dispatchers.IO) {
//            val map = mapForRepositoryListAgaintBookmarks(remoteRefactoredData)
//
//            for (repository in remoteRefactoredData) {
//                val isBookmarked =
//                    if (map.containsKey(repository.repositoryId)) map[repository.repositoryId]!! else false
//                val owner = OwnerLocalModel(
//                    author = repository.owner.author,
//                    avatarUrl = repository.owner.avatarUrl
//                )
//                repositoryList.add(
//                    RepositoryLocalModel(
//                        name = repository.name,
//                        repositoryId = repository.repositoryId,
//                        description = repository.description,
//                        isBookMarked = isBookmarked,
//                        owner = owner,
//                        createdAt = repository.createdAt,
//                        htmlUrl = repository.htmlUrl,
//                    )
//                )
//            }
//        }
//        return repositoryList
//    }
//
//    private suspend fun mapForRepositoryListAgaintBookmarks(allFetchedRepositories: List<RepositoryLocalModel>): HashMap<Int, Boolean> {
//        val listOfRepositoryId = mutableListOf<Int>()
//        for (repo in allFetchedRepositories) {
//            listOfRepositoryId.add(repo.repositoryId)
//        }
//        val bookmarks = bookmarkDao.getBookmarks(listOfRepositoryId)
//        val hashMap = hashMapOf<Int, Boolean>()
//        for (bookmark in bookmarks!!) {
//            hashMap[bookmark.repositoryId] = bookmark.isBookmarked
//        }
//        return hashMap
//    }
//}

class MapperRepositoryListForBookmarks @Inject constructor(
) :
    Function2<List<RepositoryLocalModel>, HashMap<Int, Boolean>, List<RepositoryLocalModel>> {
    override fun invoke(remoteRefactoredData: List<RepositoryLocalModel>, hashBookmarks:HashMap<Int, Boolean>): List<RepositoryLocalModel> {
        val repositoryList = mutableListOf<RepositoryLocalModel>()

        for (repository in remoteRefactoredData) {
            val isBookmarked = if(hashBookmarks.containsKey(repository.repositoryId)) hashBookmarks[repository.repositoryId]!! else false
            val owner = OwnerLocalModel(
                author = repository.owner.author,
                avatarUrl = repository.owner.avatarUrl
            )
            repositoryList.add(
                RepositoryLocalModel(
                    name = repository.name,
                    repositoryId = repository.repositoryId,
                    description = repository.description,
                    isBookMarked = isBookmarked,
                    owner = owner,
                    createdAt = repository.createdAt,
                    htmlUrl = repository.htmlUrl,
                )
            )
        }

        return repositoryList
    }
}