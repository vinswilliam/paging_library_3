package com.example.testcermati.user.data

import com.example.testcermati.api.BaseDataSource
import com.example.testcermati.api.UserGithubService

class UserGithubRemoteDataSource(private val service: UserGithubService) : BaseDataSource() {

    suspend fun fetchSets(page: Int, query: String? = null) = getResult { service.getUser(query, page) }
}