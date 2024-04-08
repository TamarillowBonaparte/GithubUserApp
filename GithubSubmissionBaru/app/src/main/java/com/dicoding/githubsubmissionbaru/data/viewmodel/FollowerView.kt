package com.dicoding.githubsubmissionbaru.data.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dicoding.githubsubmissionbaru.data.response.ItemsItem
import com.dicoding.githubsubmissionbaru.data.retrofit.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FollowerView : ViewModel(){
    private val _user = MutableLiveData<List<ItemsItem>?>()
    val user: LiveData<List<ItemsItem>?> = _user

    private val _isloading = MutableLiveData<Boolean>()
    val isloading: LiveData<Boolean> = _isloading


    fun findFollowers(username: String) {
        _isloading.value = true
        val client = ApiConfig.getApiService().getUserFollowers(username)
        client.enqueue(object : Callback<List<ItemsItem>> {
            override fun onResponse(
                call: Call<List<ItemsItem>>,
                response: Response<List<ItemsItem>>
            ) {
                _isloading.value = false
                if (response.isSuccessful) {
                    _user.value = response.body()
                }
            }

            override fun onFailure(call: Call<List<ItemsItem>>, t: Throwable) {
                _isloading.value = false
            }
        })
    }
}