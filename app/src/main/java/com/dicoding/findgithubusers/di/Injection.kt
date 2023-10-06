package com.dicoding.findgithubusers.di

import android.content.Context
import com.dicoding.findgithubusers.data.FavoritesUserRepository
import com.dicoding.findgithubusers.data.local.room.FavoritesDatabase
import com.dicoding.findgithubusers.data.remote.retrofit.ApiConfig
import com.dicoding.findgithubusers.utils.AppExecutors

object Injection {
    fun provideRepository(context: Context): FavoritesUserRepository {
        val apiService = ApiConfig.getApiService()
        val database = FavoritesDatabase.getInstance(context)
        val dao = database.favoritesDao()
        val appExecutors = AppExecutors()
        return FavoritesUserRepository.getInstance(apiService, dao, appExecutors)
    }
}