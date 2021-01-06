package com.example.testcermati

import androidx.lifecycle.ViewModelProvider
import com.example.testcermati.user.data.UserGithubRepository
import com.example.testcermati.user.ui.ViewModelFactory

object Injection {
    private fun provideGithubRepository(): UserGithubRepository {
        return UserGithubRepository()
    }

    fun provideViewModelFactory(): ViewModelProvider.Factory {
        return ViewModelFactory(provideGithubRepository())
    }
}