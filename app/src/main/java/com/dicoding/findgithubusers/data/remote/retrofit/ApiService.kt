package com.dicoding.findgithubusers.data.remote.retrofit

import com.dicoding.findgithubusers.data.remote.response.DetailsUserResponse
import com.dicoding.findgithubusers.data.remote.response.GithubResponse
import com.dicoding.findgithubusers.data.remote.response.ItemsItem
import retrofit2.Call
import retrofit2.http.*

interface ApiService {
    @GET("search/users")
    fun getGithubUser(
        @Query("q") id: String
    ): Call<GithubResponse>

    @GET("users/{username}")
    fun getDetailUser(@Path("username") username: String): Call<DetailsUserResponse>

    @GET("users/{username}/followers")
    fun getFollowers(@Path("username") username: String): Call<List<ItemsItem>>

    @GET("users/{username}/following")
    fun getFollowing(@Path("username") username: String): Call<List<ItemsItem>>
}