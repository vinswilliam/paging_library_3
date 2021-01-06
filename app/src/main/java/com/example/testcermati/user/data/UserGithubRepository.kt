package com.example.testcermati.user.data

import androidx.paging.*
import com.example.testcermati.api.UserGithubService
import com.example.testcermati.createRetrofit
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow

@ExperimentalCoroutinesApi
class UserGithubRepository {

//    private val db = AppDatabase.getInstance(context)
//    private val dao: UserGithubDao = db.userGithubDao()
    private val userGithubRemoteDataSource: UserGithubRemoteDataSource =
        UserGithubRemoteDataSource(createRetrofit(UserGithubService::class.java))

    fun getSearchResultStream(query: String): Flow<PagingData<UserGithub>> {
        return Pager(
            config = PagingConfig(
                pageSize = NETWORK_PAGE_SIZE,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { UserGithubPageDataSource(query, userGithubRemoteDataSource)}
        ).flow
    }

    companion object {
        private const val NETWORK_PAGE_SIZE = 50
    }

}