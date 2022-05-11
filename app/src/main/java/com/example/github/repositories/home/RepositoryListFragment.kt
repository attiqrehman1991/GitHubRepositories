package com.example.github.repositories.home

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.github.repositories.R
import com.example.github.repositories.databinding.FragmentRepositoryListBinding
import com.example.github.repositories.common.view.repositoryList.RepositoryListAdapter
import com.example.github.repositories.common.local.RepositoryLocalModel

class RepositoryListFragment : Fragment() {

    interface RepositoryInteractionInterface {
        fun refresh()
        fun updateBookmarkForRepository(repositoryId: Int, newStatus: Boolean)
        fun repositorySelected(repository: RepositoryLocalModel)
    }

    private var _binding: FragmentRepositoryListBinding? = null
    private val binding get() = _binding!!

    private var parentListener: RepositoryInteractionInterface? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (parentFragment is RepositoryInteractionInterface) {
            parentListener = parentFragment as RepositoryInteractionInterface
        }
    }

    override fun onDetach() {
        super.onDetach()
        parentListener = null
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRepositoryListBinding.inflate(inflater, container, false)
        val view = binding.root
        initListener()
        return view
    }

    private fun initListener() {
        binding.retryToLoad.setOnClickListener { parentListener?.refresh() }
        binding.swipeRefresh.setOnRefreshListener { parentListener?.refresh() }
    }

    private fun checkDataExistsAndShowErrorAndHideSwipeRefreshBar(
        articles: List<RepositoryLocalModel>?,
        view: View?,
        message: String
    ) {
        binding.swipeRefresh.isRefreshing = false
        if (articles.isNullOrEmpty()) {
            view?.visibility = View.GONE
            binding.retryToLoad.visibility = View.VISIBLE
            binding.emptyListMessage.visibility = View.VISIBLE
            binding.emptyListMessage.text = message
        } else {
            view?.visibility = View.VISIBLE
            binding.retryToLoad.visibility = View.GONE
            binding.emptyListMessage.visibility = View.GONE
        }
    }

    fun observeRepositoryList(repositories: Result<List<RepositoryLocalModel>>) {
        if (repositories.getOrNull() != null) {
            setUpRepositoryList(binding.repositoryList, repositories.getOrNull()!!)
        }
        checkDataExistsAndShowErrorAndHideSwipeRefreshBar(
            repositories.getOrNull(),
            binding.repositoryList,
            if (repositories.exceptionOrNull()?.message.isNullOrEmpty()) getString(R.string.network_error) else repositories.exceptionOrNull()?.message!!
        )
    }

    private fun setUpRepositoryList(
        view: RecyclerView?,
        repositoryList: List<RepositoryLocalModel>
    ) {
        with(view as RecyclerView) {
            layoutManager = LinearLayoutManager(context)
            adapter = RepositoryListAdapter(
                repositoryList,
                object : RepositoryListAdapter.RepositoryListBehaviour {
                    override fun repositorySelected(repository: RepositoryLocalModel) {
                        parentListener?.repositorySelected(repository)
                    }

                    override fun repositoryBookmarkChanged(repositoryId: Int, newStatus: Boolean) {
                        parentListener?.updateBookmarkForRepository(repositoryId, newStatus)
                    }
                })
        }
    }

}