package com.example.github.repositories.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.github.repositories.R
import com.example.github.repositories.databinding.FragmentHomeRepositoryListBinding
import com.example.github.repositories.common.local.RepositoryLocalModel
import com.example.github.repositories.home.viewmodel.HomeViewModel
import com.example.github.repositories.home.viewmodel.HomeViewModelFactory
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class HomeRepositoryListFragment : Fragment(),
    RepositoryListFragment.RepositoryInteractionInterface {
    lateinit var viewModel: HomeViewModel

    @Inject
    lateinit var viewModelFactory: HomeViewModelFactory

    private var _binding: FragmentHomeRepositoryListBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeRepositoryListBinding.inflate(inflater, container, false)
        val view = binding.root
        setUpViewModel()

        addRepositoryListFragment(R.id.frameRepositoryList)
        observeLoader()
        observeRepositoryList()

        return view
    }

    private var repositoryListFragment: RepositoryListFragment? = null
    private fun addRepositoryListFragment(layoutFrame: Int) {
        try {
            repositoryListFragment = RepositoryListFragment()
            val transaction = childFragmentManager.beginTransaction()
            transaction.replace(layoutFrame, repositoryListFragment!!).commit()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun observeRepositoryList() {
        viewModel.repositories.observe(this as LifecycleOwner) { repositories ->
            repositoryListFragment?.let {
                repositoryListFragment!!.observeRepositoryList(repositories)
            }
        }
    }

    private fun observeLoader() {
        viewModel.loader.observe(this as LifecycleOwner) { loading ->
            when (loading) {
                true -> binding.loader.visibility = View.VISIBLE
                false -> binding.loader.visibility = View.GONE
            }
        }
    }

    private fun setUpViewModel() {
        viewModel = ViewModelProvider(this, viewModelFactory).get(HomeViewModel::class.java)
    }

    override fun refresh() {
        viewModel.refresh()
    }

    override fun updateBookmarkForRepository(repositoryId: Int, newStatus: Boolean) {
        viewModel.updateBookmarkForRepository(repositoryId, newStatus)
    }

    override fun repositorySelected(repository: RepositoryLocalModel) {
        val action =
            HomeRepositoryListFragmentDirections.actionRepositoryListFragmentToRepositoryDetailFragment(
                repository
            )
        findNavController().navigate(action)
    }


}