package com.dicoding.githubsubmissionbaru.data.retrofit

import com.dicoding.githubsubmissionbaru.data.response.ItemsItem
import com.dicoding.githubsubmissionbaru.data.response.ResponseDetailGithub
import com.dicoding.githubsubmissionbaru.data.response.ResponseGithub
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("search/users")
    fun getUser(
        @Query("q") login: String
    ): Call<ResponseGithub>

    @GET("users/{username}")
    fun getDetailUser(
        @Path("username") username: String
    ): Call<ResponseDetailGithub>

    @GET("users/{username}/followers")
    fun getUserFollowers(
        @Path("username") username: String
    ): Call<List<ItemsItem>>

    @GET("users/{username}/following")
    fun getUserFollowing(
        @Path("username") username: String
    ): Call<List<ItemsItem>>
}