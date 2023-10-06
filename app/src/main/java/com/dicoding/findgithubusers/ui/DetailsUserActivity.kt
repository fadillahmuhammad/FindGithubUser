package com.dicoding.findgithubusers.ui

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.dicoding.findgithubusers.R
import com.dicoding.findgithubusers.data.local.entity.FavoriteUser
import com.dicoding.findgithubusers.databinding.ActivityDetailsUserBinding
import com.dicoding.findgithubusers.di.ViewModelFactory
import com.dicoding.findgithubusers.viewmodel.DetailsUserViewModel
import com.dicoding.findgithubusers.viewmodel.FollowersViewModel
import com.dicoding.findgithubusers.viewmodel.FollowingViewModel
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class DetailsUserActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailsUserBinding
    private val followersViewModel by viewModels<FollowersViewModel>()
    private val followingViewModel by viewModels<FollowingViewModel>()
    private val detailsUserViewModel by viewModels<DetailsUserViewModel> {
        ViewModelFactory.getInstance(application)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        binding = ActivityDetailsUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val username = intent.getStringExtra(USERNAME_KEY)
        val avatarUrl = intent.getStringExtra(AVATAR_KEY)

        if (username != null) {
            detailsUserViewModel.getDetailsUser(username)

            detailsUserViewModel.getFavoriteUserByUsername(username).observe(this) { favoriteUser ->
                val user = FavoriteUser(username, avatarUrl)

                if (favoriteUser == null) {
                    binding.fabFavorite.setOnClickListener {
                        detailsUserViewModel.saveFavorites(user)
                        binding.fabFavorite.setImageResource(R.drawable.baseline_favorite_24)
                    }
                } else {
                    binding.fabFavorite.setImageResource(R.drawable.baseline_favorite_24)
                    binding.fabFavorite.setOnClickListener {
                        detailsUserViewModel.deleteFavorites(favoriteUser)
                        binding.fabFavorite.setImageResource(R.drawable.baseline_favorite_border_24)
                    }
                }
            }
        }

        detailsUserViewModel.detailsUser.observe(this) { detailUser ->
            binding.tvName.text = detailUser.name
            binding.tvUsername.text = detailUser.login
            Glide.with(this).load(detailUser.avatarUrl).into(binding.ivAvatar)
            binding.followerCountTextView.text = detailUser.followers.toString()
            binding.followingCountTextView.text = detailUser.following.toString()
        }

        detailsUserViewModel.isLoading.observe(this) {
            showLoading(it)
        }
        followersViewModel.isLoading.observe(this) {
            showLoading(it)
        }
        followingViewModel.isLoading.observe(this) {
            showLoading(it)
        }

        val sectionsPagerAdapter = SectionsPagerAdapter(this)
        if (username != null) {
            sectionsPagerAdapter.username = username

            val viewPager: ViewPager2 = binding.viewPager
            viewPager.adapter = sectionsPagerAdapter

            val tabs: TabLayout = binding.tabs
            TabLayoutMediator(tabs, viewPager) { tab, position ->
                tab.text = when (position) {
                    0 -> {
                        followersViewModel.getFollowers(username)
                        "Followers"
                    }

                    1 -> {
                        followingViewModel.getFollowing(username)
                        "Following"
                    }

                    else -> ""
                }
            }.attach()
        }
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    companion object {
        const val USERNAME_KEY = "username_data"
        const val AVATAR_KEY = "avatar_data"
    }
}