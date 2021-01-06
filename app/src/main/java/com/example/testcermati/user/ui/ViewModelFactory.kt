package com.example.testcermati.user.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.testcermati.user.data.UserGithubRepository
import java.lang.IllegalArgumentException

class ViewModelFactory(private val repository: UserGithubRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(UserGithubViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return UserGithubViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }

}