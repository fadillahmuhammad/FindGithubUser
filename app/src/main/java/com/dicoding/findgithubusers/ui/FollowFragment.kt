package com.dicoding.findgithubusers.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.findgithubusers.data.remote.response.ItemsItem
import com.dicoding.findgithubusers.databinding.FragmentFollowBinding
import com.dicoding.findgithubusers.viewmodel.FollowersViewModel
import com.dicoding.findgithubusers.viewmodel.FollowingViewModel
import com.dicoding.findgithubusers.viewmodel.MainViewModel

class FollowFragment : Fragment() {
    private lateinit var mainViewModel: MainViewModel
    private lateinit var followersViewModel: FollowersViewModel
    private lateinit var followingViewModel: FollowingViewModel

    private var _binding: FragmentFollowBinding? = null
    private val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.let {
            val position = it.getInt(ARG_POSITION)

            if (position == 1) {
                binding.rvFollow.layoutManager = LinearLayoutManager(requireActivity())
                followersViewModel.listFollowers.observe(viewLifecycleOwner) { listFollowers ->
                    binding.progressBar.visibility = View.GONE
                    setFollow(listFollowers)
                }
            } else {
                binding.rvFollow.layoutManager = LinearLayoutManager(requireActivity())
                followingViewModel.listFollowing.observe(viewLifecycleOwner) { listFollowing ->
                    binding.progressBar.visibility = View.GONE
                    setFollow(listFollowing)
                }
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFollowBinding.inflate(inflater, container, false)
        mainViewModel = ViewModelProvider(requireActivity())[MainViewModel::class.java]
        followersViewModel = ViewModelProvider(requireActivity())[FollowersViewModel::class.java]
        followingViewModel = ViewModelProvider(requireActivity())[FollowingViewModel::class.java]
        return binding.root
    }

    private fun setFollow(consumerUsers: List<ItemsItem>?) {
        val adapter = ListUsersAdapter()
        binding.rvFollow.adapter = adapter
        adapter.submitList(consumerUsers)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        const val ARG_POSITION: String = "0"
    }
}