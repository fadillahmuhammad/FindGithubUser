package com.dicoding.findgithubusers.di

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.dicoding.findgithubusers.data.FavoritesUserRepository
import com.dicoding.findgithubusers.viewmodel.DetailsUserViewModel
import com.dicoding.findgithubusers.viewmodel.FavoritesViewModel

class ViewModelFactory private constructor(private val favoritesUserRepository: FavoritesUserRepository) :
    ViewModelProvider.NewInstanceFactory() {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DetailsUserViewModel::class.java)) {
            return DetailsUserViewModel(favoritesUserRepository) as T
        } else if (modelClass.isAssignableFrom(FavoritesViewModel::class.java)) {
            return FavoritesViewModel(favoritesUserRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
    }

    companion object {
        @Volatile
        private var instance: ViewModelFactory? = null
        fun getInstance(context: Context): ViewModelFactory =
            instance ?: synchronized(this) {
                instance ?: ViewModelFactory(Injection.provideRepository(context))
            }.also { instance = it }
    }
}