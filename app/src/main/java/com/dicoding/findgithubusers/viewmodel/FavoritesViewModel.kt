package com.dicoding.findgithubusers.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.dicoding.findgithubusers.data.FavoritesUserRepository
import com.dicoding.findgithubusers.data.local.entity.FavoriteUser

class FavoritesViewModel(favoritesRepository: FavoritesUserRepository) : ViewModel() {

    val favoriteUsers: LiveData<List<FavoriteUser>> = favoritesRepository.getFavoriteUsers()
}
