package com.example.github.repositories.user_detail_repository_list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.github.repositories.R
import com.example.github.repositories.common.local.RepositoryLocalModel
import com.example.github.repositories.databinding.FragmentUserDetailWithRepositoryListBinding
import com.example.github.repositories.home.RepositoryListFragment
import com.example.github.repositories.user_detail_repository_list.data_model.local.UserDetailLocal
import com.example.github.repositories.user_detail_repository_list.view_model.UserDetailRepoListViewModel
import com.example.github.repositories.user_detail_repository_list.view_model.UserDetailRepoListViewModelFactory
import com.squareup.picasso.Picasso
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class UserDetailRepositoryListFragment : Fragment(),
    RepositoryListFragment.RepositoryInteractionInterface {

    private val args: UserDetailRepositoryListFragmentArgs by navArgs()
    lateinit var viewModel: UserDetailRepoListViewModel

    @Inject
    lateinit var viewModelFactory: UserDetailRepoListViewModelFactory

    private var _binding: FragmentUserDetailWithRepositoryListBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentUserDetailWithRepositoryListBinding.inflate(inflater, container, false)
        val view = binding.root

        addRepositoryListFragment(R.id.frameRepositoryList)

        val owner = args.owner
        binding.title.text = owner.author
        owner.avatarUrl?.let {
            Picasso.get().load(owner.avatarUrl).into(binding.image)
        }

        setUpViewModel()
        if (viewModel.owner.isEmpty())
            viewModel.owner = owner.author
        initListeners()
        observers()
        observeUserDetail(owner.author)
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

    private fun observers() {
        observeLoader()
        observeUserDetail()
        observeRepositoryList()
    }

    private fun observeRepositoryList() {
        viewModel.repositories.observe(this as LifecycleOwner) { repositories ->
            repositoryListFragment?.let {
                repositoryListFragment!!.observeRepositoryList(repositories)
            }
        }
    }

    private fun observeUserDetail() {
        viewModel.userDetail.observe(this as LifecycleOwner) { userDetail ->
            if (userDetail.getOrNull() != null) {
                if (!userDetail.getOrNull()!!.twitterUsername.isNullOrEmpty()) {
                    binding.detail.visibility = View.VISIBLE
                    binding.detail.text =
                        "Twitter handle: " + userDetail.getOrNull()!!.twitterUsername
                } else
                    binding.detail.visibility = View.GONE
                viewModel.getRepositories(viewModel.owner)
            }
            handleErrorViewForUserDetail(
                userDetail.getOrNull(),
                if (userDetail.exceptionOrNull()?.message.isNullOrEmpty()) getString(
                    R.string.network_error
                ) else userDetail.exceptionOrNull()?.message!!
            )
        }
    }

    private fun initListeners() {
        binding.viewError.retryToLoad.setOnClickListener {
            viewModel.getUserDetail(viewModel.owner)
        }
    }

    private fun handleErrorViewForUserDetail(userDetail: UserDetailLocal?, errorMessage: String) {
        if (userDetail != null)
            binding.viewError.layoutError.visibility = View.GONE
        else {
            binding.viewError.layoutError.visibility = View.VISIBLE
            binding.viewError.emptyListMessage.text = errorMessage
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
        viewModel =
            ViewModelProvider(this, viewModelFactory).get(UserDetailRepoListViewModel::class.java)
    }

    private fun observeUserDetail(author: String) {
        viewModel.getUserDetail(author)
    }

    override fun refresh() {
        viewModel.refresh()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun updateBookmarkForRepository(repositoryId: Int, newStatus: Boolean) {
        viewModel.updateBookmarkForRepository(repositoryId, newStatus)
    }

    override fun repositorySelected(repository: RepositoryLocalModel) {
        val action =
            UserDetailRepositoryListFragmentDirections.actionUserDetailWithRepositoryListFragmentToRepositoryDetailFragment(
                repository
            )
        findNavController().navigate(action)
    }

}