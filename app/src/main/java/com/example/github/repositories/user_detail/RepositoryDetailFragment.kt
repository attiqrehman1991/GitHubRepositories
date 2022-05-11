package com.example.github.repositories.user_detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.github.repositories.common.local.RepositoryLocalModel
import com.example.github.repositories.databinding.FragmentRepositoryDetailBinding
import com.example.mercariandroidtemplate.imageloader.ImageLoader
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class RepositoryDetailFragment : Fragment() {
    @Inject
    lateinit var imageLoader: ImageLoader
    private val args: RepositoryDetailFragmentArgs by navArgs()
    private var _binding: FragmentRepositoryDetailBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRepositoryDetailBinding.inflate(inflater, container, false)
        val view = binding.root
        val repository: RepositoryLocalModel = args.repository

        binding.title.text = repository.name
        binding.detail.text =
            "Created by " + repository.owner.author + ", at " + repository.createdAt
        repository.owner.avatarUrl?.let {
            imageLoader.displayImage(it, binding.image)
        }
        binding.description.text = repository.description
        binding.url.text = repository.htmlUrl

        binding.detail.setOnClickListener {
            val action =
                RepositoryDetailFragmentDirections.actionRepositoryDetailFragmentToUserDetailWithRepositoryListFragment(
                    repository.owner
                )
            findNavController().navigate(action)
        }
        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}