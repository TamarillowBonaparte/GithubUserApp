package com.dicoding.githubsubmissionbaru.data.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.dicoding.githubsubmissionbaru.data.repository.Repository
import com.dicoding.githubsubmissionbaru.data.response.ItemsItem
import com.dicoding.githubsubmissionbaru.data.response.ResponseGithub
import com.dicoding.githubsubmissionbaru.data.retrofit.ApiConfig
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModell(private val repositoryUser: Repository) : ViewModel() {
    private val _listUser = MutableLiveData<List<ItemsItem>?>()
    val listUser: LiveData<List<ItemsItem>?> = _listUser

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    companion object {
        private const val TAG = "MainActivity"
        private const val LOGIN = "Mojang"
    }

    init {
        findUser()
    }

    private fun findUser() {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getUser(LOGIN)
        client.enqueue(object : Callback<ResponseGithub> {
            override fun onResponse(
                call: Call<ResponseGithub>,
                response: Response<ResponseGithub>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    _listUser.value = response.body()?.items
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }
            override fun onFailure(call: Call<ResponseGithub>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure: ${t.message}")
            }
        })
    }

    fun getThemeSettings(): LiveData<Boolean> {
        return repositoryUser.getThemeSetting().asLiveData()
    }

    fun saveThemeSetting(isDarkModeActive: Boolean) {
        viewModelScope.launch {
            repositoryUser.saveThemeSetting(isDarkModeActive)
        }
    }
}