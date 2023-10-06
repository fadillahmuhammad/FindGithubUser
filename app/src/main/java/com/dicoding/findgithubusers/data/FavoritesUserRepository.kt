package com.dicoding.findgithubusers.data

import androidx.lifecycle.LiveData
import com.dicoding.findgithubusers.data.local.entity.FavoriteUser
import com.dicoding.findgithubusers.data.local.room.FavoritesDao
import com.dicoding.findgithubusers.data.remote.retrofit.ApiService
import com.dicoding.findgithubusers.utils.AppExecutors
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class FavoritesUserRepository private constructor(
    private val apiService: ApiService,
    private val favoritesDao: FavoritesDao,
    private val appExecutors: AppExecutors
) {

    suspend fun insertFavoriteUser(user: FavoriteUser) {
        withContext(Dispatchers.IO) {
            favoritesDao.insert(user)
        }
    }

    suspend fun deleteFavoriteUser(user: FavoriteUser) {
        withContext(Dispatchers.IO) {
            favoritesDao.delete(user)
        }
    }

    fun getFavoriteUserByUsername(username: String): LiveData<FavoriteUser> {
        return favoritesDao.getFavoriteUserByUsername(username)
    }

    fun getFavoriteUsers(): LiveData<List<FavoriteUser>> {
        return favoritesDao.getAllFavoriteUser()
    }

    companion object {
        @Volatile
        private var instance: FavoritesUserRepository? = null
        fun getInstance(
            apiService: ApiService,
            newsDao: FavoritesDao,
            appExecutors: AppExecutors
        ): FavoritesUserRepository =
            instance ?: synchronized(this) {
                instance ?: FavoritesUserRepository(apiService, newsDao, appExecutors)
            }.also {
                instance = it
            }
    }
}