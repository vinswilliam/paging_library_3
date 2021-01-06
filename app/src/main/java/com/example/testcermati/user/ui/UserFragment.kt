package com.example.testcermati.user.ui

import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.observe
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.paging.map
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.testcermati.Injection
import com.example.testcermati.databinding.UserFragmentBinding
import com.example.testcermati.user.data.UserGithub
import com.example.testcermati.user.data.UserGithubRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChangedBy
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.launch

@ExperimentalCoroutinesApi
class UserFragment : Fragment() {

    private lateinit var binding: UserFragmentBinding
    private lateinit var viewModel: UserGithubViewModel

    private val adapterUserGithub: UserGithubAdapter = UserGithubAdapter()

    private var searchJob: Job? = null

    private fun search(query: String) {
        if (query.isNotEmpty()) {
            searchJob?.cancel()
            searchJob = lifecycleScope.launch {
                viewModel.searchUser(query).collect { pagingData : PagingData<UserGithub> ->
                    adapterUserGithub.submitData(pagingData)
                }
            }
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = UserFragmentBinding.inflate(inflater, container, false)

        viewModel = ViewModelProvider(this, Injection.provideViewModelFactory())
            .get(UserGithubViewModel::class.java)

        val decoration = DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL)

        binding.rvUser.addItemDecoration(decoration)

        binding.rvUser.adapter = adapterUserGithub
        binding.btnRetry.setOnClickListener {
            adapterUserGithub.retry()
        }

        initAdapter()

        val query = savedInstanceState?.getString(LAST_SEARCH_QUERY) ?: DEFAULT_QUERY
        search(query)
        initSearch(query)

        return binding.root
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString(LAST_SEARCH_QUERY, binding.etSearch.text.trim().toString())
    }

    private fun initAdapter() {
        binding.rvUser.adapter = adapterUserGithub.withLoadStateHeaderAndFooter(
            header = UserLoadStateAdapter(adapterUserGithub),
            footer = UserLoadStateAdapter(adapterUserGithub)
        )

        adapterUserGithub.addLoadStateListener { loadState ->

            binding.rvUser.isVisible = loadState.source.refresh is LoadState.NotLoading

            binding.progressBar.isVisible = loadState.source.refresh is LoadState.Loading

            binding.btnRetry.isVisible = loadState.source.refresh is LoadState.Error

            val errorState = loadState.source.append as? LoadState.Error
                ?: loadState.source.prepend as? LoadState.Error
                ?: loadState.append as? LoadState.Error
                ?: loadState.append as? LoadState.Error

            errorState?.let {
                Toast.makeText(requireContext(), "\uD83D\uDE28 Upps, ${it.error}", Toast.LENGTH_LONG)
                    .show()
            }

            if (loadState.source.refresh is LoadState.NotLoading
                && loadState.append.endOfPaginationReached
                && adapterUserGithub.itemCount < 1) {
                binding.rvUser.isVisible = false
                binding.emptyView.isVisible = true
            } else {
                binding.rvUser.isVisible = true
                binding.emptyView.isVisible = false
            }
        }
    }

    private fun initSearch(query: String) {
        binding.etSearch.setText(query)

        binding.etSearch.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_GO) {
                updateRepoListFromInput()
                true
            } else {
                false
            }
        }

        binding.etSearch.setOnKeyListener { _, keyCode, event ->
            if (event.action == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER) {
                updateRepoListFromInput()
                true
            } else {
                false
            }
        }

        lifecycleScope.launch {
            adapterUserGithub.loadStateFlow
                .distinctUntilChangedBy { it.refresh }
                .filter { it.refresh is LoadState.NotLoading }
                .collect { binding.rvUser.scrollToPosition(0) }
        }
    }

    private fun updateRepoListFromInput() {
        binding.etSearch.text.trim().let {
            if (it.isNotEmpty()) {
                binding.rvUser.scrollToPosition(0)
                search(it.toString())
            }
        }
    }

    companion object {
        private const val LAST_SEARCH_QUERY: String = "last_search_query"
        private const val DEFAULT_QUERY = ""
    }
}