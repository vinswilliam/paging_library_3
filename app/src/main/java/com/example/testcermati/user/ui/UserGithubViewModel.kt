package com.example.testcermati.user.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.testcermati.user.data.UserGithub
import com.example.testcermati.user.data.UserGithubRepository
import kotlinx.coroutines.flow.Flow

class UserGithubViewModel(private val repository: UserGithubRepository) : ViewModel() {

    private var currentQueryValue: String? = null
    private var currentSearchResult: Flow<PagingData<UserGithub>>? = null

    fun searchUser(queryString: String): Flow<PagingData<UserGithub>> {
        val lastResult = currentSearchResult
        if (queryString == currentQueryValue && lastResult != null) {
            return lastResult
        }

        currentQueryValue = queryString
        val newResult: Flow<PagingData<UserGithub>> = repository.getSearchResultStream(queryString)
            .cachedIn(viewModelScope)
        currentSearchResult = newResult
        return newResult
    }

}