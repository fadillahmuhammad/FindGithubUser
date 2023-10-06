package com.dicoding.findgithubusers.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dicoding.findgithubusers.data.FavoritesUserRepository
import com.dicoding.findgithubusers.data.local.entity.FavoriteUser
import com.dicoding.findgithubusers.data.remote.response.DetailsUserResponse
import com.dicoding.findgithubusers.data.remote.retrofit.ApiConfig
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailsUserViewModel(private val favoritesUserRepository: FavoritesUserRepository) :
    ViewModel() {

    private val _detailsUser = MutableLiveData<DetailsUserResponse>()
    val detailsUser: LiveData<DetailsUserResponse> = _detailsUser

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    companion object {
        private const val TAG = "DetailsUserViewModel"
    }

    fun getDetailsUser(username: String) {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getDetailUser(username)
        client.enqueue(object : Callback<DetailsUserResponse> {
            override fun onResponse(
                call: Call<DetailsUserResponse>,
                response: Response<DetailsUserResponse>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    _detailsUser.postValue(response.body())
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<DetailsUserResponse>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure: ${t.message.toString()}")
            }
        })
    }

    fun saveFavorites(favoriteUser: FavoriteUser) {
        viewModelScope.launch {
            favoritesUserRepository.insertFavoriteUser(favoriteUser)
        }
    }

    fun deleteFavorites(favoriteUser: FavoriteUser) {
        viewModelScope.launch {
            favoritesUserRepository.deleteFavoriteUser(favoriteUser)
        }
    }

    fun getFavoriteUserByUsername(username: String): LiveData<FavoriteUser> {
        return favoritesUserRepository.getFavoriteUserByUsername(username)
    }

}