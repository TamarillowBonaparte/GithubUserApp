package com.dicoding.githubsubmissionbaru.data.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.dicoding.githubsubmissionbaru.data.local.DataUser
import com.dicoding.githubsubmissionbaru.data.repository.Repository

class FavoritView(private val repositoryUser: Repository) : ViewModel(){
    fun getUsers() : LiveData<List<DataUser>>{
        return repositoryUser.getUsers()
    }
}