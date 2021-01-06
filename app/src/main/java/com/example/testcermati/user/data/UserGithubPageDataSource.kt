package com.example.testcermati.user.data

import androidx.paging.PagingSource
import com.example.testcermati.data.Result
import retrofit2.HttpException
import java.io.IOException

private const val GITHUB_STARTING_PAGE_INDEX = 1

class UserGithubPageDataSource(
    private val query: String? = null,
    private val dataSource: UserGithubRemoteDataSource
//    private val dao: UserGithubDao
) : PagingSource<Int, UserGithub>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, UserGithub> {
        val position = params.key ?: GITHUB_STARTING_PAGE_INDEX

        return try {
            val response = dataSource.fetchSets(position, query)

            if (response.status == Result.Status.SUCCESS) {
                val repos = response.data?.items!!
                LoadResult.Page(
                    data = repos,
                    prevKey = if (position == GITHUB_STARTING_PAGE_INDEX) null else position - 1,
                    nextKey = if (repos.isEmpty()) null else position + 1
                )
            } else {
                LoadResult.Error(Exception(response.message))
            }

        } catch (exception: IOException) {
            return LoadResult.Error(exception)
        } catch (exception: HttpException) {
            return LoadResult.Error(exception)
        }
    }

}